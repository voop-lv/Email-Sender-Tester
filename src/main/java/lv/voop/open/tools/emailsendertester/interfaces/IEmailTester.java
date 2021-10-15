package lv.voop.open.tools.emailsendertester.interfaces;

import lv.voop.open.tools.emailsendertester.manager.CoreManager;

import java.io.File;
import java.util.logging.Logger;

public interface IEmailTester {
        File getConfigurationFile();
        CoreManager getManager();
        Logger getLogger();
}
