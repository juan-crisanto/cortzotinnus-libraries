package pe.cortzotinnus.security.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConfigurationConstant {

    public static final int AUTHENTICATION_CONFIG_ORDER = 100;
    public static final int JWT_AUTHORIZATION_ORDER = SecurityConfigurationConstant.AUTHENTICATION_CONFIG_ORDER + 100;
    public static final int BASIC_AUTHENTICATION_ORDER = SecurityConfigurationConstant.JWT_AUTHORIZATION_ORDER + 100;
    public static final String AUTHENTICATION_ERROR_CODE = "AUTHENTICATION_ERROR";
    public static final String UNSUCCESSFUL_AUTHENTICATION_DEFAULT_MESSAGE = "Usuario y/o contraseña incorrectos";
}
