package lv.voop.open.tools.emailsendertester.manager;

import com.electronwill.nightconfig.core.file.FileConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lv.voop.open.tools.emailsendertester.EmailSenderTester;
import lv.voop.open.tools.emailsendertester.interfaces.EmailTesterManager;
import lv.voop.open.tools.emailsendertester.utils.ConfigDefaultValueChecker;

public class ConfigurationManager implements EmailTesterManager {

    @Getter private static boolean newFile;
    @Getter private ConfigurationData data;

    @Override @SneakyThrows public void onEnable() {
        newFile = !EmailSenderTester.getInstance().getConfigurationFile().exists();
        if (newFile) EmailSenderTester.getInstance().getConfigurationFile().createNewFile();
        new ConfigDefaultValueChecker(FileConfig.of(EmailSenderTester.getInstance().getConfigurationFile()))
                .append("debug.state",false)
                .append("credentials.host","replace")
                .append("credentials.username","replace@replace.replace")
                .append("credentials.password","replace@replace.replace")
                .append("credentials.port",465)
                .append("credentials.ssl",false)
                .append("credentials.starttls",false)
                .append("credentials.auth",false)
                .execute();
        this.data = new ConfigurationData();
        this.data.loadData();
    }

    public static class ConfigurationData {

        @Getter @Setter private String credentials_host;
        @Getter @Setter private String credentials_username;
        @Getter @Setter private String credentials_password;
        @Getter @Setter private int credentials_port;
        @Getter @Setter private boolean credentials_ssl;
        @Getter @Setter private boolean credentials_auth;
        @Getter @Setter private boolean credentials_starttls;

        @Getter private boolean debug;

        public void loadData() {
            var cfg = FileConfig.of(EmailSenderTester.getInstance().getConfigurationFile());
            cfg.load();
            this.debug = cfg.get("debug.state");
            this.credentials_host = cfg.get("credentials.host");
            this.credentials_username = cfg.get("credentials.username");
            this.credentials_password = cfg.get("credentials.password");
            this.credentials_port = cfg.get("credentials.port");
            this.credentials_ssl = cfg.get("credentials.ssl");
            this.credentials_auth = cfg.get("credentials.auth");
            this.credentials_auth = cfg.get("credentials.starttls");
            cfg.close();
        }
        
        public void updateCFG(){
            var cfg = FileConfig.of(EmailSenderTester.getInstance().getConfigurationFile());
            cfg.load();
            if (this.credentials_host!=null) cfg.set("credentials.host",this.credentials_host);
            if (this.credentials_username!=null) cfg.set("credentials.username",this.credentials_username);
            if (this.credentials_password!=null) cfg.set("credentials.password",this.credentials_password);
            cfg.set("credentials.port",this.credentials_port);
            cfg.set("credentials.ssl",this.credentials_ssl);
            cfg.set("credentials.auth",this.credentials_auth);
            cfg.set("credentials.starttls",this.credentials_starttls);
            cfg.save();
            cfg.close();
        }
    }
}