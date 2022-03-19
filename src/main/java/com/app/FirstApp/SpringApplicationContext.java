package com.app.FirstApp;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

// Conteneur d'objet dans notre exmple il va contenir l'objet UserservicesImpl
// si classe et @Component or Interface donc pour access à leur mehodes necessite ApplicationContext
public class SpringApplicationContext implements ApplicationContextAware {
	private static ApplicationContext CONTEXT;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		//System.out.println("i get the context");
		CONTEXT = context;

	}

	// we use this methode in AuthenticationFilter in successfulAuthentication
	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}

}
