package weapon.yuziouo;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Loader extends PluginBase {
    static Loader loader;
    public ArrayList<String>weaponsname= new ArrayList<>();
    public ArrayList<String>armorsname= new ArrayList<>();
    public ArrayList<String>skillnames = new ArrayList<>();
    File file;
    @Override
    public void onEnable() {
        loader = this;
        file = new File(getDataFolder()+"/Weapon/");
        if (!file.exists())file.mkdirs();
        file = new File(getDataFolder()+"/Armor/");
        if (!file.exists())file.mkdirs();
        file = new File(getDataFolder()+"/Skill/");
        if (!file.exists())file.mkdirs();
        file = null;
        loadWeapon();
        loadArmor();
        loadSkill();
        new Weapon();
        new Armor();
        new Skill();
        getServer().getPluginManager().registerEvents(new WeaponListener(),this);
        getServer().getCommandMap().register("wp",new YWCommand());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static Loader getLoader() {
        return loader;
    }
    public void loadWeapon(){
        File folder = new File(getDataFolder()+"/Weapon/");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles!= null){
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String[] a = file.getName().split("\\.");
                if (a[1].equals("yml"))
                weaponsname.add(a[0]);
                }
            System.out.println("武器讀取完成:"+weaponsname);
            }
        }
    }
    public void loadArmor(){
        File folder = new File(getDataFolder()+"/Armor/");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles!= null){
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String[] a = file.getName().split("\\.");
                    if (a[1].equals("yml"))
                    armorsname.add(a[0]);
                }
                System.out.println("防具讀取完成:"+armorsname);
            }
        }
    }
    public void loadSkill(){
        File folder = new File(getDataFolder()+"/Skill/");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles!= null){
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String[] a = file.getName().split("\\.");
                    if (a[1].equals("yml"))
                    skillnames.add(a[0]);
                }
                System.out.println("技能讀取完成:"+skillnames);
            }
        }
    }
    public Config getWeapon(String name){
        return new Config(getDataFolder()+"/Weapon/"+name+".yml",Config.YAML);
    }
    public Config getArmor(String name){
        return new Config(getDataFolder()+"/Armor/"+name+".yml",Config.YAML);
    }
    public Config getSkill(String name){
        return new Config(getDataFolder()+"/Skill/"+name+".yml",Config.YAML);
    }
    public  String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}