package weapon.yuziouo;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class WeaponListener implements Listener {
    HashMap<Player,Integer> dmg = new HashMap<>(),tk = new HashMap<>(),hl = new HashMap<>();
    HashMap<Player,HashMap<String,Integer>> sktask = new HashMap<>();
    @EventHandler
    public void onJoin(PlayerLocallyInitializedEvent event){
        Player player = event.getPlayer();
        sktask.put(player,new HashMap<>());
        dmg.put(player,0);
        tk.put(player,0);
        hl.put(player,0);
    }
    @EventHandler
    public void onQuite(PlayerQuitEvent event){
        sktask.remove(event.getPlayer());
        dmg.remove(event.getPlayer(),0);
        tk.remove(event.getPlayer(),0);
        hl.remove(event.getPlayer(),0);
    }
    @EventHandler
    public void onTap(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (player.getInventory().getItemInHand().hasCompoundTag()){
            if (player.getInventory().getItemInHand().getNamedTag().contains(Weapon.Tag)){
                if (player.getInventory().getItemInHand().getNamedTag().contains("skls")){
                    String name = player.getInventory().getItemInHand().getNamedTag().getString("skls");
                    if (!sktask.get(player).containsKey(name)) {
                        Config config = Loader.getLoader().getSkill(name);
                        String[] aa;
                        if (config.getStringList("技能")!=null) {
                            for (String a : config.getStringList("技能")) {
                                if (a.contains("傷害附近生物")) {
                                    aa = a.split(":");
                                    Skill.skill.dagSkill(player, Integer.parseInt(aa[1]),Integer.parseInt(aa[2]));
                                }
                                if (a.contains("擊退附近怪物")) {
                                    aa = a.split(":");
                                    Skill.skill.kbSkill(player, Integer.parseInt(aa[1]));
                                }
                                if (a.contains("回復玩家血量")) {
                                    aa = a.split(":");
                                    Skill.skill.healSkill(player, Integer.parseInt(aa[1]));
                                }
                                if (a.contains("吸引生物")){
                                    aa = a.split(":");
                                    Skill.skill.come(player,Integer.parseInt(aa[1]));
                                }
                                if (a.contains("點燃附近生物")){
                                    aa = a.split(":");
                                    Skill.skill.fire(player,Integer.parseInt(aa[1]),Integer.parseInt(aa[2]));
                                }
                                if (a.contains("發送訊息給自己")){
                                    aa = a.split(":");
                                    Skill.skill.sendMessagetoUser(player,aa[1]);
                                }

                                if (a.contains("發送訊息給附近玩家")){
                                    aa = a.split(":");
                                    Skill.skill.sendMessagetoNearEntity(player,aa[1],Integer.parseInt(aa[2]));
                                }
                                if (a.contains("發送底部訊息給自己")){
                                    aa = a.split(":");
                                    Skill.skill.sendPopuptoUser(player,aa[1]);
                                }
                                if (a.contains("發送底部訊息給附近玩家")){
                                    aa = a.split(":");
                                    Skill.skill.sendPopuptoNearEntity(player,aa[1],Integer.parseInt(aa[2]));
                                }
                                if (a.contains("發送標題給自己")){
                                    aa = a.split(":");
                                    Skill.skill.sendTitletoUser(player,aa[1]);
                                }
                                if (a.contains("發送標題給附近玩家")){
                                    aa = a.split(":");
                                    Skill.skill.sendTitletoNearEntity(player,aa[1],Integer.parseInt(aa[2]));
                                }
                                if (a.contains("閃電擊中附近玩家")){
                                    aa = a.split(":");
                                    Skill.skill.lighting(player,Integer.parseInt(aa[1]));
                                }
                            }
                        }
                                if (config.getStringList("藥水效果自身") != null) {
                                    for (String s : config.getStringList("藥水效果自身")) {
                                        aa = s.split(":");
                                        cn.nukkit.potion.Effect effect = cn.nukkit.potion.Effect.getEffect(Integer.parseInt(aa[0]));
                                        effect.setAmplifier(Integer.parseInt(aa[1]));
                                        effect.setDuration(20 * Integer.parseInt(aa[2]));
                                        player.addEffect(effect);
                                    }
                                }
                                if (config.getStringList("藥水效果附近生物") != null) {
                                    ArrayList<cn.nukkit.potion.Effect> effects = new ArrayList<>();
                                    for (String s : config.getStringList("藥水效果附近生物")) {
                                        aa = s.split(":");
                                        cn.nukkit.potion.Effect effect = cn.nukkit.potion.Effect.getEffect(Integer.parseInt(aa[0]));
                                        effect.setAmplifier(Integer.parseInt(aa[1]));
                                        effect.setDuration(20 * Integer.parseInt(aa[2]));
                                        effects.add(effect);
                                    }
                                    Skill.skill.addEntityEffect(player, effects);
                                }
                        sktask.get(player).put(name, config.getInt("技能冷卻"));
                        Server.getInstance().getScheduler().scheduleDelayedTask(Loader.getLoader(), new Runnable() {
                            @Override
                            public void run() {
                                sktask.get(player).remove(name);
                            }
                        },20*sktask.get(player).get(name));
                    }else {
                        player.sendMessage("技能冷卻中 稍後再使用");
                    }
                }
            }
        }
    }
    @EventHandler
    public void onHold(PlayerItemHeldEvent event){
        Item item = event.getItem();
        Player player = event.getPlayer();
        if (item.hasCompoundTag()){
            if (item.getNamedTag().contains(Weapon.Tag)){
                if (!Loader.getLoader().weaponsname.contains(item.getNamedTag().getString(Weapon.Tag))&&!Loader.getLoader().armorsname.contains(item.getNamedTag().getString(Weapon.Tag))){
                    player.getInventory().removeItem(item);
                    player.sendMessage("很抱歉優 這項物品已經被移除瞜");
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageEvent event){
        if (event.isCancelled())return;
        if (event instanceof EntityDamageByEntityEvent){
            if (((EntityDamageByEntityEvent) event).getDamager() instanceof Player){
                Player player = (Player) ((EntityDamageByEntityEvent) event).getDamager();
                for (Item itm :player.getInventory().getArmorContents()) {
                    if (itm.getId() != 0) {
                        if (itm.hasCompoundTag()) {
                            if (itm.getNamedTag().contains(Weapon.Tag)) {
                                if (!dmg.containsKey(player)) {
                                    dmg.put(player, itm.getNamedTag().getInt("dmg"));
                                } else {
                                    int a = dmg.get(player);
                                    dmg.remove(player);
                                    dmg.put(player, a + itm.getNamedTag().getInt("dmg"));
                                }

                            }
                        }
                    }
                }
                if (player.getInventory().getItemInHand().hasCompoundTag()){
                    if (player.getInventory().getItemInHand().getNamedTag().contains(Weapon.Tag)){
                        Random random = new Random();
                        int damage = player.getInventory().getItemInHand().getNamedTag().getInt("dmg");
                        if (dmg.containsKey(player)) {
                             damage = player.getInventory().getItemInHand().getNamedTag().getInt("dmg") + dmg.get(player);
                        }
                        int hc = player.getInventory().getItemInHand().getNamedTag().getInt("hc");
                        int hs = player.getInventory().getItemInHand().getNamedTag().getInt("hs");
                        int cc = player.getInventory().getItemInHand().getNamedTag().getInt("cc");
                        int cdmg = player.getInventory().getItemInHand().getNamedTag().getInt("cdmg");
                        float sda = (event.getDamage()+damage);
                        if (random.nextInt(100)<cc){
                            float d = 1+cdmg /100;
                            float dam = sda*d;
                            event.setDamage(dam);
                            Effect.addMaxDamage(event.getEntity().getPosition());
                            player.sendPopup("你已經打出爆擊傷害:"+ TextFormat.RED +dam+"點傷害了!");
                        }else {
                            event.setDamage(sda);
                        }
                        if (random.nextInt(100)<hc){
                            float a = 1+hs/100;
                            float hhc = (player.getHealth()+event.getDamage())*a;
                            player.setHealth(hhc);
                            player.sendPopup(TextFormat.RED+"你已經吸取"+event.getEntity().getName()+" "+hhc+"點血量");
                        }
                        if (dmg.containsKey(player))
                            dmg.remove(player);
                    }
                }else {
                    if (dmg.containsKey(player)) {
                        event.setDamage(event.getDamage() + dmg.get(player));
                        dmg.remove(player);
                    }
                }
            }
        }
    }
    @EventHandler
    public void getDamage(EntityDamageEvent event){
        if (event instanceof EntityDamageByEntityEvent){
            if (event.getEntity() instanceof  Player) {
                Player player = (Player) event.getEntity();
                for (Item itm : player.getInventory().getArmorContents()) {
                    if (itm.getId() != 0) {
                        if (itm.hasCompoundTag()) {
                            if (itm.getNamedTag().contains(Weapon.Tag)) {
                                if (!tk.containsKey(player)) {
                                    tk.put(player, itm.getNamedTag().getInt("tk"));
                                } else {
                                    int a = tk.get(player);
                                    tk.remove(player);
                                    tk.put(player, a + itm.getNamedTag().getInt("tk"));
                                }

                            }
                        }
                    }
                }
                if (tk.containsKey(player)) {
                    float f = event.getDamage() - (event.getDamage() * (tk.get(player) / 10));
                    event.setDamage(f);
                    tk.remove(player);
                }
            }
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        for (Item itm :player.getInventory().getArmorContents()) {
            if (itm.getId() != 0) {
                if (itm.hasCompoundTag()) {
                    if (itm.getNamedTag().contains(Weapon.Tag)) {
                        if (!hl.containsKey(player)) {
                            hl.put(player, itm.getNamedTag().getInt("hl"));
                        } else {
                            int a = hl.get(player);
                            hl.remove(player);
                            hl.put(player, a + itm.getNamedTag().getInt("hl"));
                        }
                    }
                }
            }
        }
        if (hl.containsKey(player)) {
            player.setMaxHealth(20 + hl.get(player));
            hl.remove(player);
        }else {
            player.setMaxHealth(20);
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if (event.getItem().hasCompoundTag()){
            if (event.getItem().getNamedTag().contains(Weapon.Tag)){
                event.setCancelled(true);
            }
        }
    }
}
