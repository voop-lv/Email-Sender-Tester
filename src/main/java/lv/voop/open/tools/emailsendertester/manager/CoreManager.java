package lv.voop.open.tools.emailsendertester.manager;

import lombok.Getter;
import lv.voop.open.tools.emailsendertester.EmailSenderTester;
import lv.voop.open.tools.emailsendertester.interfaces.EmailTesterManager;

import java.util.HashSet;
import java.util.logging.Level;

public class CoreManager {
    private static boolean enabled_state = false;

    private HashSet<EmailTesterManager> managers;
    @Getter private ConfigurationManager configurationManager;
    @Getter private TextBasedMenu textBasedMenu;

    public void enable(){
        if (enabled_state) throw new IllegalStateException("Email Tester is already enabled!");
        this.managers = new HashSet<>();
        this.configurationManager = new ConfigurationManager();
        loadManager(this.configurationManager);
        if (!ConfigurationManager.isNewFile()) {
            EmailSenderTester.getInstance().getLogger().log(Level.INFO,"Staring Up Other Managers!");
            this.textBasedMenu = new TextBasedMenu();
            loadManager(this.textBasedMenu);
        } else {
            EmailSenderTester.getInstance().getLogger().warning("Voiding Boot up on other managers closing program due to the new file is created! Extra configuration is need it!");
            System.exit(0);
        }
        enabled_state = true;
    }
    public void disable(){
        if (!enabled_state) throw new IllegalStateException("Email Tester is already disabled!");
        HashSet<EmailTesterManager> managers_clone = new HashSet<>(this.managers);
        managers_clone.forEach(this::unloadManager);
        enabled_state = false;
    }

    public void loadManager(EmailTesterManager manager){
        if (this.managers.contains(manager)) return;
        try {
            manager.onEnable();
            this.managers.add(manager);
        } catch (Exception e){
            EmailSenderTester.getInstance().getLogger().severe("Failed to load "+manager.getClass().getSimpleName()+" manager due to a exception "+e);
            e.printStackTrace();
        }
    }

    public void unloadManager(EmailTesterManager manager){
        if (!this.managers.contains(manager)) return;
        try {
            manager.onDisable();
            this.managers.remove(manager);
        } catch (Exception e){
            EmailSenderTester.getInstance().getLogger().severe("Failed to unload "+manager.getClass().getSimpleName()+" manager due to a exception "+e);
            e.printStackTrace();
        }
    }
}
