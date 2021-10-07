package weapon.yuziouo;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import java.util.ArrayList;
import java.util.Random;

public class Weapon {
    static Weapon weapon;
    public static String Tag = "YouAreSoAwesome";
    public Weapon(){
        weapon = this;
    }
    public static Weapon getWeapon() {
        return weapon;
    }
    public Item toWeapon(String name){
        Config config = Loader.getLoader().getWeapon(name);
        Item item = Item.get(config.getInt("id"),config.getInt("meta"),1);
        item.setNamedTag(getTag(name));
        item.setCustomName(name);
        item.addEnchantment(Enchantment.get(Enchantment.ID_DURABILITY).setLevel(10,false));
        String[] setLore = getLore(item).toArray(new String[getLore(item).size()]);
        item.setLore(setLore);
        return item;
    }
    public CompoundTag getTag(String name){
        Config config = Loader.getLoader().getWeapon(name);
        CompoundTag compoundTag= new CompoundTag();
        if (!config.getBoolean("是否可以破壞")){
            compoundTag.putByte("Unbreakable",1);
        }
        Random random = new Random();
        compoundTag
                .putString(Tag,name)
                .putInt("dmg",random.nextInt(config.getInt("傷害高")-config.getInt("傷害低"))+config.getInt("傷害低"))
                .putInt("hc",random.nextInt(config.getInt("吸血機率高")-config.getInt("吸血機率低"))+config.getInt("吸血機率低"))
                .putInt("hs",random.nextInt(config.getInt("吸血比例高")-config.getInt("吸血比例低"))+config.getInt("吸血比例低"))
                .putInt("cc",random.nextInt(config.getInt("爆擊機率高")-config.getInt("爆擊機率低"))+config.getInt("爆擊機率低"))
                .putInt("cdmg",random.nextInt(config.getInt("爆擊傷害加成高")-config.getInt("爆擊傷害加成低"))+config.getInt("爆擊傷害加成低"))

                .putString("loree",config.getString("lore"));
        if (config.getString("技能裝載")!= null) {
            compoundTag.putString("skls",config.getString("技能裝載"));
        }
        return compoundTag;
    }
    public ArrayList<String> getLore(Item item){
        ArrayList<String> lore = new ArrayList<>();
        lore.add(TextFormat.WHITE+"------物品介紹--------");
        lore.add(item.getNamedTag().getString("loree"));
        lore.add(TextFormat.WHITE+"------物品介紹---------");
        lore.add(TextFormat.RED+"攻擊力: +"+item.getNamedTag().getInt("dmg"));
        lore.add(TextFormat.RED+"爆擊機率: +"+item.getNamedTag().getInt("cc"));
        lore.add(TextFormat.RED+"爆擊傷害加成: +"+item.getNamedTag().getInt("cdmg"));
        lore.add(TextFormat.RED+"吸血機率: +"+item.getNamedTag().getInt("hc"));
        lore.add(TextFormat.RED+"吸血比例加成: +"+item.getNamedTag().getInt("hs"));
        lore.add(TextFormat.WHITE+"---------------------");
        if (item.getNamedTag().contains("skls")){
            lore.add(TextFormat.WHITE+"武器技能:"+ item.getNamedTag().getString("skls"));
            lore.add(TextFormat.WHITE+"技能介紹:"+Loader.getLoader().getSkill(item.getNamedTag().getString("skls")).getString("技能介紹"));
        }
        lore.add(TextFormat.AQUA+"獲取時間: " +Loader.getLoader().getStringDate());
        return lore;
    }
    public void init(String name){
        if (!Loader.getLoader().weaponsname.contains(name)){
            Config config = new Config(Loader.getLoader().getDataFolder()+"/Weapon/"+name+".yml",Config.YAML);
            config.set("id",1);
            config.set("meta",0);
            config.set("lore","測試用得武器 請去後台修改把");
            config.set("是否可以破壞",true);
            config.set("傷害高",5);
            config.set("傷害低",2);
            config.set("吸血機率高",100);
            config.set("吸血機率低",0);
            config.set("吸血比例高",10);
            config.set("吸血比例低",2);
            config.set("爆擊機率高",100);
            config.set("爆擊機率低",0);
            config.set("爆擊傷害加成高",20);
            config.set("爆擊傷害加成低",10);
            config.set("技能裝載",null);
            config.save();
            Loader.getLoader().weaponsname.add(name);
        }
    }
}
