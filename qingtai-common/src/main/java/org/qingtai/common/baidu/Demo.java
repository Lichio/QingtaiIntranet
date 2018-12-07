package org.qingtai.common.baidu;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.ocr.AipOcr;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * org.qingtai.common.ocr
 * Created on 2018/11/26
 *
 * @author Lichaojie
 */
public class Demo {
	private static AipOcr instance;

	//设置APPID/AK/SK
	private static final String APP_ID = "14947384";
	private static final String API_KEY = "6WRjMPTGXgQ1BDFbiSbzTuQx";
	private static final String SECRET_KEY = "0FLEhhCZ45eSlSYk6LSFk4TRhssIUbby";

	public static AipOcr getAipOcr(){
		if(instance == null){
			synchronized (Demo.class){
				if(instance == null){
					// 初始化一个AipOcr
					instance = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

					// 可选：设置网络连接参数
					instance.setConnectionTimeoutInMillis(2000);
					instance.setSocketTimeoutInMillis(60000);

					// 可选：设置代理服务器地址, http和socket二选一，或者均不设置
					// client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
					// client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

					// 可选：设置log4j日志输出格式，若不设置，则使用默认配置
					// 也可以直接通过jvm启动参数设置此环境变量
					// System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

				}
			}
		}
		return instance;
	}



	/**
	 * 文字识别
	 * @param file
	 */
	private Object ocrBasicGeneral(String file){

		AipOcr client = Demo.getAipOcr();

		// 调用接口
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("language_type", "CHN_ENG");
		options.put("detect_direction", "true");
		options.put("detect_language", "true");
//		options.put("probability", "true");
//		JSONObject res = client.basicGeneral(file, options);
		JSONObject res = client.accurateGeneral(file, options);
		System.out.println(res.toString(2));
//		System.out.println(res.getJSONObject("words_result").toString(2));
		return res;
	}

	/**
	 * 人脸检测
	 * @param appId
	 * @param apiKey
	 * @param secretKey
	 * @throws IOException
	 */
	@Deprecated
	private void faceDetect(String appId, String apiKey, String secretKey, String file) throws IOException{
		// 初始化一个AipFace
		AipFace client = new AipFace(appId, apiKey, secretKey);

		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		// 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//		client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//		client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

		// 可选：设置log4j日志输出格式，若不设置，则使用默认配置
		// 也可以直接通过jvm启动参数设置此环境变量
//		System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

		// 调用接口
		String image = Base64Util.encode(Util.readFileByBytes(file));
		String imageType = "BASE64";

		HashMap<String,String> options = new HashMap<String, String>();
		options.put("face_type", "CERT");

		JSONObject res = client.detect(image, imageType, options);
		System.out.println(res.toString(2));
	}

	private boolean checkSchool(List<Object> results){
		StringBuffer sb = new StringBuffer();
		HashMap<String,String> hm;
		for (Object object : results){
			hm = (HashMap<String,String>)object;
			sb.append(hm.get("words"));
		}
		String s = sb.toString().toLowerCase();
		return StringUtils.isNotBlank(s) && StringUtils.contains(s, "xidian");
	}

	/**
	 * 提取用户信息
	 * @param file
	 * @return
	 */
	public Object getUser(String file){
		JSONObject json = (JSONObject) ocrBasicGeneral(file);
		User user = new User();
		if (json.has("error_code")){
			System.out.println(json.get("error_code"));
			return "101";
		}else {
			List<Object> objects = json.getJSONArray("words_result").toList();
			if(!checkSchool(objects)){
				// 将所有输出信息改为打印到日志
				System.out.println("Authentication failed.");
				return "102";
			}
			HashMap<String, String> hm;
			String str;
			for (Object object : objects){
				hm = (HashMap<String, String>) object;
				str = hm.get("words");
				if (str.contains("姓名")){
					// 问题：此处只判断是否为汉字，如果识别结果有错，将得到错误的结果
					// 解决：无法解决（识别结果是否需要回显让用户进行纠错）
					str = str.substring(str.indexOf("姓名") + 3);
					StringBuilder name = new StringBuilder();
					for (char c : str.toCharArray()){
						if (c >= 0x4e00 && c<= 0x9fa5){
							name.append(c);
						}else {
							break;
						}
					}
					user.setName(name.toString());
				}else if(str.contains("学院")){
					// 问题：此处只判断是否为汉字，如果识别结果有错，将得到错误的结果
					// 解决：可根据学校的学院设置情况与结果进行匹配，对结果进行纠错
					str = str.substring(str.indexOf("学院") + 3);
					StringBuilder academy = new StringBuilder();
					for (char c : str.toCharArray()){
						if (c >= 0x4e00 && c<= 0x9fa5){
							academy.append(c);
						}else {
							break;
						}
					}
					user.setAcademy(academy.toString());
				}else if (str.contains("班级")){
					// 问题：同上
					// 解决：可提取班级的正则模式对结果进行纠错处理（如模式是否都是 [\u4e00-\u9fa5][0-9]{4}）
					str = str.substring(str.indexOf("班级") + 3);
					StringBuilder clas = new StringBuilder();
					for (char c : str.toCharArray()){
						if ((c >= 0x4e00 && c<= 0x9fa5) || (c >= 0x30 && c <= 0x39)){
							clas.append(c);
						}else {
							break;
						}
					}
					user.setClas(clas.toString());
				}else if ((str.contains("学号"))){
					// 问题：同上
					// 解决：不同学院的学号为固定长度，可根据长度做纠错处理
					str = str.substring(str.indexOf("学号") + 3);
					StringBuilder stuNum = new StringBuilder();
					for (char c : str.toCharArray()){
						if (c >= 0x30 && c <= 0x39){
							stuNum.append(c);
						}else {
							break;
						}
					}
					user.setStuNum(stuNum.toString());
				}
			}

		}
		return user;
	}

	public static void main(String[] args) throws IOException {

		String file = "C:\\Users\\lich_\\Desktop\\LEEN\\test\\image8.jpg";

		Object object = new Demo().getUser(file);
		if(object instanceof User){
			System.out.println(object.toString());
		}else {
			String result = (String)object;
			if ("101".equals(result)){
				System.out.println("OCR failed.");
			}else if ("102".equals(result)){
				System.out.println("Authentication failed.");
			}
		}
	}

	class User{
		private String name;// 姓名
		private String academy; // 学院
		private String clas; // 班级
		private String stuNum; // 学号

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAcademy() {
			return academy;
		}

		public void setAcademy(String academy) {
			this.academy = academy;
		}

		public String getClas() {
			return clas;
		}

		public void setClas(String clas) {
			this.clas = clas;
		}

		public String getStuNum() {
			return stuNum;
		}

		public void setStuNum(String stuNum) {
			this.stuNum = stuNum;
		}

		@Override
		public String toString(){
			return "Name: " + name + '\n' +
					"Academy: " + academy + '\n' +
					"Class: " + clas + '\n' +
					"Stu-Number: " + stuNum + '\n';
		}
	}
}
