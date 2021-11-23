package src;
public class ProjectExtenstion extends Project {
    private String extensionCode;
    private ProjectExtenstion(String title, String code, String eCode) {
        super(title, code);
        extensionCode = eCode;
    }
    public static ProjectExtenstion create(String title, String pCode, int eCode) {
        String extensionCode = String.format("E%d", eCode);                                // ExtensionProject is concerned about extension format
        ProjectExtenstion extension = new ProjectExtenstion(title, pCode, extensionCode); // not Project
        return extension;
    }
    @Override
    public String getCode() {
        return super.getCode() + "-" + extensionCode;
    }
}
