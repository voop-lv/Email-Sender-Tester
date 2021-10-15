package lv.voop.open.tools.emailsendertester.interfaces;

public interface EmailTesterManager {
    void onEnable();
    default void onDisable(){}
}
