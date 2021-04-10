# Yany's Stuff & Things Addon
Are you literally me? Then this addon might just be the thing for you. It changes BTW - especially the early game - to make it more interesting for my own preferred play style. You can find out more in the changelog in the repo, or in the associated thread on the BTW forums [here](https://sargunster.com/btwforum/viewtopic.php?f=12&t=9873).

# Installation
You can install the addon to your Better Than Wolves instance by adding the contents of the released zip to your minecraft.jar, the same way you installed Better Than Wolves.

You should not install my Boat Mapping and Click Bait addons together with this one, as those are already included in their own way.

I have not made a lot of effort to keep this compatible with other addons. If another addon modifies the same classes, you can expect it to be incompatible with this one for the time being.

# Using the source code

The source code released in the repo is based on the decompiled source code of Better Than Wolves. You can find more information about obtaining the decompiled source code [here](https://www.sargunster.com/btw/index.php?title=Creating_Addons). My own files, and the ones that I have changed from Better Than Wolves are included in their entirety. The changes I made to vanilla base classes are available as a git patch that you can apply to the decompiled source code with `git apply --directory=src src.patch`. 

## The patch modifies:
```
ContainerWorkbench.java
EntityCreature.java
EntityCreeper.java
EntityFishHook.java
EntityItem.java
EntityPlayer.java
EntityPlayerMP.java
FoodStats.java
ItemFishingRod.java
ItemMap.java
```
Client only:
```
GuiIngame.java
TileEntityRenderer.java
```

# Copyright info
The Stuff & Things Addon is available under the CC0 1.0 license, allowing you to do with it as you wish without considering my copyright. The Stuff & Things Addon may include content based on Mojang's work. If anything included in the addon can be found in Minecraft, Mojang's terms and conditions may apply. The Stuff & Things Addon contains modified files from the Better Than Wolves mod, created by FlowerChild, and available under the CC BY 4.0 license.

While CC0 does not require attribution, I would still appreciate a mention if you end up distributing work based on this addon.