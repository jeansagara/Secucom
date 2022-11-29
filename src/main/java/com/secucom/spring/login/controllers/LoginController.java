package com.secucom.spring.login.controllers;

import com.secucom.spring.login.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.Map;
import java.util.logging.Logger;


@RestController
public class LoginController{
    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

    @Autowired
    private   OAuth2AuthorizedClientService authorizedClientService;

    //        @RolesAllowed("USER")
//        @RequestMapping("/**")
//        public String getUser()
//        {
//            return "Welcome User";
//        }
//
//        @RolesAllowed({"USER","ADMIN"})
//        @RequestMapping("/admin")
//        public String getAdmin()
//        {
//            return "Welcome Admin";
//        }
//
//        @RequestMapping("/*")
//        public String getGithub(Principal user){
//            return user.toString();
//        }
//    @RequestMapping("/*")
//
//    public String getUserInfo(Principal user) {
//        StringBuffer userInfo= new StringBuffer();
//        if(user instanceof UsernamePasswordAuthenticationToken){
//            userInfo.append(getUsernamePasswordLoginInfo(user));
//        }
//        else if(user instanceof OAuth2AuthenticationToken){
//            userInfo.append(getOauth2LoginInfo(user));
//        }
//        return userInfo.toString();
//    }
//    private StringBuffer getUsernamePasswordLoginInfo(Principal user)
//    {
//        StringBuffer usernameInfo = new StringBuffer();
//
//        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
//        if(token.isAuthenticated()){
//            User u = (User) token.getPrincipal();
//            usernameInfo.append("Welcome, " + u.getUsername());
//        }
//        else{
//            usernameInfo.append("NA");
//        }
//        return usernameInfo;
//    }
    @RequestMapping("/**")
    private StringBuffer getOauth2LoginInfo(Principal user){
        OAuth2User principal = ((OAuth2AuthenticationToken) user).getPrincipal();

        StringBuffer protectedInfo = new StringBuffer();

        OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
        OAuth2AuthorizedClient authClient = this.authorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());
        if(authToken.isAuthenticated()){

            Map<String,Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();

            String userToken = authClient.getAccessToken().getTokenValue();
            LOG.info("Bienvenu" + userAttributes.get("name")+"<br><br>");
            LOG.info("e-mail: " + userAttributes.get("email")+"<br><br>");

            protectedInfo.append("Welcome, " + userAttributes.get("name")+"<br><br>");
            protectedInfo.append("e-mail: " + userAttributes.get("email")+"<br><br>");
            OidcIdToken idToken = getIdToken(principal);
            LOG.info("Access Token: " + userToken+"<br><br>");
            protectedInfo.append("Access Token: " + userToken+"<br><br>");
            if(idToken != null) {
                LOG.info("idToken value: " + idToken.getTokenValue()+"<br><br>");
                protectedInfo.append("idToken value: " + idToken.getTokenValue()+"<br><br>");
                LOG.info("Token mapped values <br><br>");
                protectedInfo.append("Token mapped values <br><br>");

                Map<String, Object> claims = idToken.getClaims();

                for (String key : claims.keySet()) {
                    protectedInfo.append("  " + key + ": " + claims.get(key)+"<br>");
                }
            }
        }
        else{
            protectedInfo.append("NA");
        }
        return protectedInfo;
    }
    public LoginController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }
    private OidcIdToken getIdToken(OAuth2User principal){
        if(principal instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser)principal;
            return oidcUser.getIdToken();
        }
        return null;
    }
}
