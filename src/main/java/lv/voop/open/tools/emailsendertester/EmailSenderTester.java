package lv.voop.open.tools.emailsendertester;

import lombok.Getter;
import lv.voop.open.tools.emailsendertester.interfaces.IEmailTester;
import lv.voop.open.tools.emailsendertester.manager.CoreManager;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailSenderTester implements IEmailTester {

    private static EmailSenderTester emailSenderTesterMainInstance;
    @Getter private static IEmailTester instance;
    private CoreManager manager;
    private File configFile;
    private Logger logger;

    @Override public File getConfigurationFile() {return this.configFile;}
    @Override public CoreManager getManager() {return this.manager;}
    @Override public Logger getLogger() {return this.logger;}

    public static void main(String[] args){
        emailSenderTesterMainInstance = new EmailSenderTester();
        emailSenderTesterMainInstance.start();
    }

    private File rootPath(){return new File(".");}

    private void start() {
        instance = this;
        this.logger = Logger.getLogger("Email Tester");
        this.logger.log(Level.INFO,"Starting Up Email Tester!");
        this.configFile = new File(rootPath(),"config.toml");
        this.manager = new CoreManager();
        this.getManager().enable();
    }

    private void stop(){
        this.getManager().disable();
    }

}
