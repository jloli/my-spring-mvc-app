package com.belatrixsf.app;

import java.io.File;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;

public class Main {

  public static void main(String[] args) throws Exception {
    Tomcat tomcat = new Tomcat();
    tomcat.setHostname("localhost");
    tomcat.setPort(8080);
    tomcat.getHost().setAppBase(".");
    tomcat.setBaseDir(new File(System.getProperty("java.io.tmpdir")).getAbsolutePath());
    StandardContext context = (StandardContext) tomcat.addWebapp("", ".");
    ((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
    tomcat.start();
    tomcat.getServer().await();
  }


}
