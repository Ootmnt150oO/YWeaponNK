package weapon.yuziouo;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityLiving;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;

import java.util.ArrayList;

public class Skill {
    static Skill skill;
    public Skill(){
        skill = this;
    }
    public Skill getSkill(){
        return skill;
    }
    public void init(String name){
        Config config = new Config(Loader.getLoader().getDataFolder()+"/Skill/"+name+".yml",Config.YAML);
        ArrayList<String> skills = new ArrayList<>(),ef = new ArrayList<>(),eef = new ArrayList<>();
        skills.add("傷害附近生物:200:200");
        skills.add("擊退附近怪物:2");
        skills.add("回復玩家血量:2");
        skills.add("吸引生物:10");
        skills.add("點燃附近生物:20:5");
        skills.add("閃電擊中附近玩家:20");
        skills.add("發送訊息給自己:哈哈我好帥");
        skills.add("發送訊息給附近玩家:你們好醜:20");
        skills.add("發送底部訊息給自己:哈哈我好帥");
        skills.add("發送底部訊息給附近玩家:你們好醜:20");
        skills.add("發送標題給自己:哈哈我好帥");
        skills.add("發送標題給附近玩家:你們好醜:20");
        config.set("技能",skills);
        config.set("技能介紹","就是既能");
        config.set("技能冷卻",5);
        ef.add("1:1:5");
        ef.add("2:1:5");
        config.set("藥水效果自身",ef);
        eef.add("3:1:5");
        eef.add("4:1:5");
        config.set("藥水效果附近生物",eef);
        config.save();
    }
    public void dagSkill(Player player,float damage,int size){
        for (Entity entity: player.getLevel().getEntities()) {
            if (entity instanceof EntityLiving) {
                if (entity.getName().equals(player.getName()))continue;
                if (entity.distance(new Vector3(player.getX(),player.getY(),player.getZ())) <= size) {
                    entity.attack(new EntityDamageByEntityEvent(entity,player, EntityDamageEvent.DamageCause.ENTITY_ATTACK,damage));
                    Effect.addMaxDamage(entity.getPosition());
                }
            }
        }
    }
    public void healSkill(Player player,float count){
        player.setHealth(player.getHealth()+count);
        Effect.addHealth(player.getPosition());
    }
    public void kbSkill(Player player,int size){
        for (Entity entity: player.getLevel().getEntities()) {
            if (entity instanceof EntityLiving) {
                if (entity.getName().equals(player.getName()))continue;
                if (entity.distance(new Vector3(player.getX(),player.getY(),player.getZ())) <= size) {
                    kb(player, entity);
                    Effect.addRelief(player.getPosition());
                }
            }
        }
    }
    public void come(Player player,int size){
        for (Entity entity: player.getLevel().getEntities()) {
            if (entity instanceof EntityLiving) {
                if (entity.getName().equals(player.getName()))continue;
                if (entity.distance(new Vector3(player.getX(),player.getY(),player.getZ())) <= size) {
                   entity.teleport(player.getPosition());
                }
            }
        }
    }
    public void addEntityEffect(Player player, ArrayList<cn.nukkit.potion.Effect> effects){
        for (Entity entity: player.getLevel().getEntities()) {
            if (entity instanceof EntityLiving) {
                if (entity.getName().equals(player.getName())) continue;
                if (entity.distance(new Vector3(player.getX(),player.getY(),player.getZ())) <= 5) {
                    for (cn.nukkit.potion.Effect effect: effects) {
                        entity.addEffect(effect);
                    }
                }
            }
        }
    }
    public void fire(Player player,int size,int sec){
        for (Entity entity: player.getLevel().getEntities()) {
            if (entity instanceof EntityLiving) {
                if (entity.getName().equals(player.getName())) continue;
                if (entity.distance(new Vector3(player.getX(),player.getY(),player.getZ())) <= size) {
                   entity.setOnFire(sec*20);
                   Effect.fire(player);
                }
            }
        }
    }
    public void sendMessagetoUser(Player player,String msg){
        player.sendMessage(msg);
    }
    public void sendMessagetoNearEntity(Player player,String msg,int size){
        for (Entity entity: player.getLevel().getEntities()) {
            if (entity instanceof EntityLiving) {
                if (entity.getName().equals(player.getName())) continue;
                if (entity.distance(new Vector3(player.getX(),player.getY(),player.getZ())) <= size) {
                    if (entity instanceof Player){
                        ((Player) entity).sendMessage(msg);
                    }
                }
            }
        }
    }
    public void sendPopuptoUser(Player player,String msg){
        player.sendPopup(msg);
    }
    public void sendPopuptoNearEntity(Player player,String msg,int size){
        for (Entity entity: player.getLevel().getEntities()) {
            if (entity instanceof EntityLiving) {
                if (entity.getName().equals(player.getName())) continue;
                if (entity.distance(new Vector3(player.getX(),player.getY(),player.getZ())) <= size) {
                    if (entity instanceof Player){
                        ((Player) entity).sendPopup(msg);
                    }
                }
            }
        }
    }
    public void sendTitletoUser(Player player ,String msg){
        player.sendTitle(msg);
    }
    public void sendTitletoNearEntity(Player player,String msg,int size){
        for (Entity entity: player.getLevel().getEntities()) {
            if (entity instanceof EntityLiving) {
                if (entity.getName().equals(player.getName())) continue;
                if (entity.distance(new Vector3(player.getX(),player.getY(),player.getZ())) <= size) {
                    if (entity instanceof Player){
                        ((Player) entity).sendTitle(msg);
                    }
                }
            }
        }
    }
    public void lighting(Player player,int size){
        for (Entity entity: player.getLevel().getEntities()) {
            if (entity instanceof EntityLiving) {
                if (entity.getName().equals(player.getName())) continue;
                if (entity.distance(new Vector3(player.getX(), player.getY(), player.getZ())) <= size) {
                    Effect.toLight(2,entity);
                }
            }
        }
    }
    public void kb(Player damager,Entity npc){
        double frontYaw = ((damager.yaw + 90.0D) * Math.PI) / 180.0D;
        double frontX = 2 * 3 * Math.cos(frontYaw);
        double frontZ = 2 * 3 * Math.sin(frontYaw);
        double frontY = 2 ;
        npc.setMotion(new Vector3(frontX, frontY, frontZ));
    }
}
