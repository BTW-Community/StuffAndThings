# Yany's Stuff & Things Addon
Are you literally me? Then this addon might just be the thing for you. It changes BTW - especially the early game - to make it more interesting for my own preferred play style. You can find out more in the changelog in the repo, or in the associated thread on the BTW forums [here](https://sargunster.com/btwforum/viewtopic.php?f=12&t=9873).

# Installation
You can install the addon to your Better Than Wolves instance by adding the contents of the released zip to your minecraft.jar, the same way you installed Better Than Wolves.

You should not install my Boat Mapping and Click Bait addons together with this one, as those are already included in their own way.

I have not made a lot of effort to keep this compatible with other addons. If another addon modifies the same classes, you can expect it to be incompatible with this one for the time being.

# Using the source code

I haven't tried setting up a working environment based on the source code, but it should generally involve

1. Obtaining the source code of BTW CE 1.3.4
2. Copying the addon source files in `minecraft` and `minecraft_server`
3. Applying `src.patch` to the above using git
4. Putting the contents of the resources wherever MCP expects them to be
5. Building it all with MCP

If you're interested in building the mod yourself and run into trouble, feel free to contact me (yany) on the BTW forums or the BTW Discord, and I'll try to help.

## The patch modifies:
```
EntityCreature.java
EntityCreeper.java
EntityFishHook.java
EntityItem.java
EntityPlayer.java
EntityPlayerMP.java
FoodStats.java
ItemMap.java
```
Client only:
```
GuiIngame.java
```

# Copyright info
The Stuff & Things Addon is available under the CC0 1.0 license, allowing you to do with it as you wish without considering my copyright. The Stuff & Things Addon may include content based on Mojang's work. If anything included in the addon can be found in Minecraft, Mojang's terms and conditions may apply. The Stuff & Things Addon contains modified files from the Better Than Wolves mod, created by FlowerChild, and available under the CC BY 4.0 license.

While CC0 does not require attribution, I would still appreciate a mention if you end up distributing work based on this addon.