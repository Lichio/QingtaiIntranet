### 使用方法

##### 添加第三方依赖包
``` 
<dependency>
	<groupId>com.baidu.aip</groupId>
    <artifactId>java-sdk</artifactId>
    <version>4.8.0</version>
</dependency>
```

##### 编译运行Demo类
Object getUser(String file)方法为提取用户信息的接口
**参数**：file为图片的本地地址
**返回值**：请求成功返回的object为User类型；否则为String类型：“101”表示OCR失败，“102”表示学校验证失败
**附**：getUser参数也可以是byte[]（图片用字节数组的形式表示），此时需要将Object ocrBasicGeneral(String file)修改为Object ocrBasicGeneral(byte[] file)，将Object getUser(String file)修改为Object getUser(byte[] file)

