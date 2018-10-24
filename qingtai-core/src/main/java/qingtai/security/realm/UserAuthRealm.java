package qingtai.security.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import qingtai.pojo.main.User;
import qingtai.security.token.Token;
import qingtai.service.interfaces.UserService;

/**
 * qingtai.security.realm
 * Created on 2018/2/2
 *
 * @author Lichaojie
 */
public class UserAuthRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal(); //得到用户名
		String password = new String((char[]) token.getCredentials()); //得到密码


		User user = userService.getUserByUsername(username);
		if (user == null) {
			throw new UnknownAccountException("用户名不存在"); //如果用户名不存在
		}

		String sha1Pass = new Sha256Hash(password, Token.PASSWORD_TOKEN).toString();
		if (!sha1Pass.endsWith(new Sha256Hash(user.getPassword(), Token.PASSWORD_TOKEN).toString())) {
			throw new IncorrectCredentialsException("密码错误"); //如果密码错误
		}

		return new SimpleAuthenticationInfo(username, sha1Pass, ByteSource.Util.bytes(Token.PASSWORD_TOKEN), getName());
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}
}
