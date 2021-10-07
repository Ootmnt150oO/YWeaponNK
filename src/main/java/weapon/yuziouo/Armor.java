package weapon.yuziouo;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import java.util.ArrayList;
import java.util.Random;

public class Armor {
    static Armor armor;
    public Armor(){
        armor = this;
    }

    public static Armor getArmor() {
        return armor;
    }

    public Item toArmor(String name){
        Config config = Loader.getLoader().getArmor(name);
        Item item = Item.get(config.getInt("id"),config.getInt("meta"),1);
        item.setNamedTag(getTag(name));
        item.setCustomName(name);
        item.addEnchantment(Enchantment.get(Enchantment.ID_DURABILITY).setLevel(10,false));
        String[] setLore = getLore(item).toArray(new String[getLore(item).size()]);
        item.setLore(setLore);
        return item;
    }
    public CompoundTag getTag(String name){
        Config config = Loader.getLoader().getArmor(name);
        CompoundTag compoundTag= new CompoundTag();
        if (!config.getBoolean("是否可以破壞")){
            compoundTag.putByte("Unbreakable",1);
        }
        Random random = new Random();
        compoundTag
                .putString(Weapon.Tag,name)
                .putInt("hl",random.nextInt(config.getInt("血量增加高")-config.getInt("血量增加低"))+config.getInt("血量增加低"))
                .putInt("dmg",random.nextInt(config.getInt("攻擊力加成高")-config.getInt("攻擊力加成低"))+config.getInt("攻擊力加成低"))
                .putInt("tk",random.nextInt(config.getInt("防禦力高")-config.getInt("防禦力低"))+config.getInt("防禦力低"))
                .putString("loree",config.getString("lore"));
        return compoundTag;
    }
    public ArrayList<String> getLore(Item item) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(TextFormat.WHITE + "------物品介紹--------");
        lore.add(item.getNamedTag().getString("loree"));
        lore.add(TextFormat.WHITE + "------物品介紹---------");
        lore.add(TextFormat.RED + "防禦力: +" + item.getNamedTag().getInt("tk"));
        lore.add(TextFormat.RED + "攻擊力: +" + item.getNamedTag().getInt("dmg"));
        lore.add(TextFormat.RED + "生命增加: +" + item.getNamedTag().getInt("hl"));
        lore.add(TextFormat.WHITE + "---------------------");
        lore.add(TextFormat.AQUA + "獲取時間: " + Loader.getLoader().getStringDate());
        return lore;
    }
    public void init(String name){
        Config config = new Config(Loader.getLoader().getDataFolder()+"/Armor/"+name+".yml",Config.YAML);
        config.set("id",310);
        config.set("meta",0);
        config.set("lore", "測試用裝備請去後台改");
        config.set("是否可以破壞",true);
        config.set("血量增加高",10);
        config.set("血量增加低",5);
        config.set("攻擊力加成高",8.0);
        config.set("攻擊力加成低",1.0);
        config.set("防禦力高",5.0);
        config.set("防禦力低",2.0);
        config.save();
        Loader.getLoader().armorsname.add(name);
    }

}
