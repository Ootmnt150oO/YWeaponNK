package weapon.yuziouo;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;

public class YWCommand extends Command {
    public YWCommand() {
        super("wp");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender.isPlayer()) {
            Player player = (Player) commandSender;
            if (player.isOp()) {
                if (strings.length == 3) {
                    if (strings[0].equals("weapon")) {
                        if (strings[1].equals("give")) {
                            if (Loader.getLoader().weaponsname.contains(strings[2])) {
                                player.getInventory().addItem(Weapon.getWeapon().toWeapon(strings[2]));
                            } else {
                                commandSender.sendMessage("目前沒有此武器");
                            }
                        } else if (strings[1].equals("add")) {
                            if (!Loader.getLoader().weaponsname.contains(strings[2])) {
                                Weapon.getWeapon().init(strings[2]);
                                commandSender.sendMessage("添加成功");
                            } else {
                                commandSender.sendMessage("此武器名稱已經被使用過");
                            }
                        }
                    } else if (strings[0].equals("armor")) {
                        if (strings[1].equals("give")) {
                            if (Loader.getLoader().armorsname.contains(strings[2])) {
                                player.getInventory().addItem(Armor.getArmor().toArmor(strings[2]));
                            } else {
                                commandSender.sendMessage("目前沒有此防具");
                            }
                        } else if (strings[1].equals("add")) {
                            if (!Loader.getLoader().armorsname.contains(strings[2])) {
                                Armor.getArmor().init(strings[2]);
                                commandSender.sendMessage("添加成功");
                            } else {
                                commandSender.sendMessage("此防具名稱已經被使用過");
                            }
                        }
                    }else if (strings[0].equals("skill")){
                        if (strings[1].equals("add")){
                            if (!Loader.getLoader().skillnames.contains(strings[2])){
                                Skill.skill.init(strings[2]);
                                commandSender.sendMessage("添加成功");
                            }else {
                                commandSender.sendMessage("此技能名稱已經被使用過瞜");
                            }
                        }
                    }
                    }if (strings.length == 7) {
                        if (strings[0].equals("weapon")) {
                            if (strings[1].equals("drop")) {
                                Level level = Server.getInstance().getLevelByName(strings[2]);
                                if (level != null) {
                                    level.dropItem(new Vector3(Integer.parseInt(strings[3]), Integer.parseInt(strings[4]), Integer.parseInt(strings[5])), Weapon.getWeapon().toWeapon(strings[6]));
                                }
                            }
                        }else if (strings[0].equals("aromr")) {
                            if (strings[1].equals("drop")) {
                                Level level = Server.getInstance().getLevelByName(strings[2]);
                                if (level != null) {
                                    level.dropItem(new Vector3(Integer.parseInt(strings[3]), Integer.parseInt(strings[4]), Integer.parseInt(strings[5])), Armor.getArmor().toArmor(strings[6]));
                                }
                            }
                        }
                }else if (strings.length == 2) {
                        if (strings[0].equals("weapon")) {
                            if (strings[1].equals("list")) {
                                player.sendMessage(String.valueOf(Loader.getLoader().weaponsname));
                            }
                        }else if (strings[0].equals("armor")){
                            if (strings[1].equals("list")) {
                                player.sendMessage(String.valueOf(Loader.getLoader().armorsname));
                            }
                        }
                    }else if (strings.length == 1){
                    if (strings[0].equals("reload")){
                        Loader.getLoader().weaponsname.clear();
                        Loader.getLoader().armorsname.clear();
                        Loader.getLoader().skillnames.clear();
                        Loader.getLoader().loadWeapon();
                        Loader.getLoader().loadArmor();
                        Loader.getLoader().loadSkill();
                        commandSender.sendMessage("讀取完畢");
                    }else {
                        commandSender.sendMessage("-----------YWeaponNk---------------");
                        commandSender.sendMessage("/wp weapon add 武器名稱     創建新武器");
                        commandSender.sendMessage("/wp weapon give 武器名稱    給予觸發指令者武器");
                        commandSender.sendMessage("/wp weapon list            顯示出全部武器");
                        commandSender.sendMessage("/wp armor add 防具名稱      創建新房具");
                        commandSender.sendMessage("/wp armor give 防具名稱     給予觸發指令者防具");
                        commandSender.sendMessage("/wp armor list            顯示出全部防具");
                        commandSender.sendMessage("/wp skill add 技能名稱 創建技能");
                        commandSender.sendMessage("/wp reload 重新讀取配置文件");
                        commandSender.sendMessage("----------------------------------");
                    }
                }
                }
            }else {
            if (strings.length == 7) {
                if (strings[0].equals("weapon")) {
                    if (strings[1].equals("drop")) {
                        Level level = Server.getInstance().getLevelByName(strings[2]);
                        if (level != null) {
                            level.dropItem(new Vector3(Double.parseDouble(strings[3]), Double.parseDouble(strings[4]), Double.parseDouble(strings[5])), Weapon.getWeapon().toWeapon(strings[6]));
                        }
                    }
                }else if (strings[0].equals("aromr")) {
                    if (strings[1].equals("drop")) {
                        Level level = Server.getInstance().getLevelByName(strings[2]);
                        if (level != null) {
                            level.dropItem(new Vector3(Double.parseDouble(strings[3]), Double.parseDouble(strings[4]), Double.parseDouble(strings[5])), Armor.getArmor().toArmor(strings[6]));
                        }
                    }
                }
            }
        }
            return true;
        }
    }
