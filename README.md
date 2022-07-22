# Yany's Stuff & Things Addon
Are you literally me? Then this addon might just be the thing for you. It changes BTW to make it more interesting for my own preferred play style. You can find out more in the changelog in the repo, or in the associated [thread](https://sargunster.com/btwforum/viewtopic.php?f=12&t=9873) on the BTW forums.

# Installation
You can install the addon to your Better Than Wolves instance by adding the contents of the released zip to your minecraft.jar, the same way you installed Better Than Wolves.

You should not install my Boat Mapping addon together with this one, as it is already included. Same goes for Hiracho's Dynamic Lighting addon in Stuff & Things 1.3.0 or newer.

I have not made a lot of effort to keep this compatible with other addons. If another addon modifies the same classes, you can expect it to be incompatible with this one for the time being. I know it is mostly compatible with Better Terrain and Deco.

# Using the source code

I haven't tried setting up a working environment based on the source code, but it should generally involve

1. Obtaining the source code of BTW CE 1.3.8 from [BTW-Public](https://github.com/BTW-Community/BTW-Public)
2. Copying the addon source files in `minecraft` and `minecraft_server`
3. Applying `src.patch` to the above using git
4. Putting the contents of the resources wherever MCP expects them to be
5. Building it all with MCP

If you're interested in building the mod yourself and run into trouble, feel free to contact me (yany) on the BTW forums or the BTW Discord, and I'll try to help.

## The patch modifies:
```
Entity.java
EntityAnimal.java
EntityArrow.java
EntityCreeper.java
EntityFireball.java
EntityFishHook.java
EntityItem.java
EntityPigZombie.java
EntityPlayer.java
EntityPlayerMP.java
EntityRenderer.java
FoodStats.java
ItemMap.java
Potion.java
PotionEffect.java
SpawnerAnimals.java
Teleporter.java
WorldGenFire.java
WorldProviderHell.java
```
Client only:
```
GuiIngame.java
```

# Copyright info
After version 1.3.0 the Stuff & Things Addon is available under the CC BY-NC-SA 4.0 license. The Stuff & Things Addon may include content based on Mojang's work. If anything included in the addon can be found in Minecraft, Mojang's terms and conditions may apply. The Stuff & Things Addon contains modified files from the Better Than Wolves mod, created by FlowerChild, and available under the CC BY 4.0 license. It also includes code from the Community Edition of Better Than Wolves, also available under the CC BY 4.0 license. It also includes code from the Dynamic Lighting addon by Hiracho, available under the CC BY-NC-SA 4.0 license.