package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

public class YYStuffAndThings extends FCAddOn
{
private static YYStuffAndThings instance = new YYStuffAndThings();
	
	public static YYStuffAndThings getInstance()
	{
		if(instance==null)
		{
			instance = new YYStuffAndThings();
		}
		return instance;
	}
	
	public static YYBlockLargeBasket yyBlockLargeBasket;
	public static int yyLargeBasketContainerID = 240;
	public static Item yyBlockItemLargeBasket;
	
	public static YYItemBarkStitching yyItemBarkStitching;
	public static Item yyItemStitchedBark;

	public static Item yyItemBait;
	public static YYItemBaitMix yyItemBaitMix;
	
	public static Item yyItemBrokenBrick;
	public static YYBlockHempBrickWet yyBlockHempBrickWet;
	public static Item yyItemHempBrickWet;
	public static YYBlockHempBrickDry yyBlockHempBrickDry;
	public static Item yyItemHempBrickDry;
	
	public static Block yyBlockTorchSparklingUnlit;
	public static Block yyBlockTorchSparklingBurning;
	
	public static YYItemYarnSpinning yyItemYarnSpinning;
	public static Item yyItemDropSpindle;
	
	public static Block yyBlockTotemSapling;
	public static YYBlockTotem yyBlockTotem;
	public static YYBlockTotemBottom yyBlockTotemBottom;
	public static Block yyBlockTotemCarving;
	
	public static FCItemFood yyItemChickenDecaying;
	public static FCItemFood yyItemBeefDecaying;
	public static FCItemFood yyItemPorkDecaying;
	public static FCItemFood yyItemMuttonDecaying;
	public static FCItemFood yyItemFishDecaying;
	
	public static FCItemFood yyItemSpoiledMeat;
	public static FCItemFood yyItemCookedMeat;
	
	public YYStuffAndThings()
	{
		super("Stuff & Things", "1.3.0", "YYST");
	}
	
	private static Map<String, String> configOptions;
	
	@Override
	public void handleConfigProperties(Map<String, String> propertyValues) {
		configOptions = propertyValues;
		yyCreeperFire = Boolean.parseBoolean(configOptions.get("CreeperFire"));
		yyHCMeat = Boolean.parseBoolean(configOptions.get("HardcoreMeat"));
		
		hcsStartDistanceMultiplier = Double.parseDouble(configOptions.get("hcsStartDistanceMultiplier"));
		hcsNetherDistanceMultiplier = Double.parseDouble(configOptions.get("hcsNetherDistanceMultiplier"));
		hcsWitherDistanceMultiplier = Double.parseDouble(configOptions.get("hcsWitherDistanceMultiplier"));
		hcsEndDistanceMultiplier = Double.parseDouble(configOptions.get("hcsEndDistanceMultiplier"));
		
		hcsStartScatterMultiplier = Double.parseDouble(configOptions.get("hcsStartScatterMultiplier"));
		hcsNetherScatterMultiplier = Double.parseDouble(configOptions.get("hcsNetherScatterMultiplier"));
		hcsWitherScatterMultiplier = Double.parseDouble(configOptions.get("hcsWitherScatterMultiplier"));
		hcsEndScatterMultiplier = Double.parseDouble(configOptions.get("hcsEndScatterMultiplier"));
	}
	
	public String getConfigOption(String option)
	{
		return configOptions.get(option);
	}

	public static boolean yyCreeperFire;
	public static boolean yyHCMeat;
	
	public static double hcsStartDistanceMultiplier;
	public static double hcsNetherDistanceMultiplier;
	public static double hcsWitherDistanceMultiplier;
	public static double hcsEndDistanceMultiplier;
	
	public static double hcsStartScatterMultiplier;
	public static double hcsNetherScatterMultiplier;
	public static double hcsWitherScatterMultiplier;
	public static double hcsEndScatterMultiplier;
	
	@Override
	public void PreInitialize() {
		this.registerProperty("CreeperFire", "True", "Set the following to false to disable creepers setting things on fire, blowing up when damaged by fire, and dropping hellfire dust.");
		this.registerProperty("HardcoreMeat", "False", "Set the following to True to enable an unfinished and unsupported gamemode, Hardcore Meat.");
		
		this.registerProperty("hcsStartDistanceMultiplier", "1.0", "The following options influence HardcoreSpawn distances. The first four push the ring of highest respawn probability further from the original spawn. The second four make you more likely to spawn further away from the ring. The ones that apply are selected based on the progression milestones achieved in the world.");
		this.registerProperty("hcsNetherDistanceMultiplier", "2.0");
		this.registerProperty("hcsWitherDistanceMultiplier", "2.0");
		this.registerProperty("hcsEndDistanceMultiplier", "2.0");
		
		this.registerProperty("hcsStartScatterMultiplier", "1.0");
		this.registerProperty("hcsNetherScatterMultiplier", "1.0");
		this.registerProperty("hcsWitherScatterMultiplier", "2.0");
		this.registerProperty("hcsEndScatterMultiplier", "3.0");
	}
	
	@Override
	public void Initialize()
	{
		FCAddOnHandler.LogMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
		
		anvilRestrictRecipes();
		reverseCE();
		
		//bowl nerf
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack(Item.bowlEmpty, 4),
			new Object[] {
				"# #",
				" # ",
				'#', Block.planks
		});
		FCRecipes.AddRecipe(
			new ItemStack(Item.bowlEmpty),
			new Object[] {
				"# #",
				" # ",
				'#', Block.planks
		});
		
		// bait
		yyItemBait = new Item(26000)
			.SetBuoyant()
			.setUnlocalizedName("yyItemBait")
			.setCreativeTab(CreativeTabs.tabFood)
			.setMaxStackSize(16);
		yyItemBaitMix = new YYItemBaitMix(26001);
		FCRecipes.AddShapelessRecipe(
			new ItemStack(yyItemBaitMix, 1, YYItemBaitMix.maxDamage-1),
			new Object[] {
				new ItemStack(Item.bowlEmpty),
				new ItemStack(Item.rottenFlesh),
				new ItemStack(Item.dyePowder, 1, 15)
		});
		FCRecipes.AddShapelessRecipe(
			new ItemStack(yyItemBait, 6),
			new Object[] {
				new ItemStack(FCBetterThanWolves.fcItemDogFood)
		});
		FCRecipes.AddCauldronRecipe(
			new ItemStack(FCBetterThanWolves.fcItemDogFood, 2),
			new ItemStack[] {
				new ItemStack(Item.rottenFlesh, 4),
				new ItemStack(Item.dyePowder, 4, 15),
				new ItemStack(Item.sugar, 1)
		});
		FCCraftingManagerCauldronStoked.getInstance().RemoveRecipe(
			new ItemStack(FCBetterThanWolves.fcItemDogFood, 2),
			new ItemStack[] {
				new ItemStack(Item.rottenFlesh, 4),
				new ItemStack(Item.dyePowder, 4, 15),
				new ItemStack(Item.sugar, 1)
		});
		
		
		// large basket
		yyBlockLargeBasket = new YYBlockLargeBasket(1500);
		TileEntity.addMapping(YYTileEntityLargeBasket.class, "yyLargeBasket");
		yyBlockItemLargeBasket = new ItemBlock(1500-256);
		FCRecipes.AddRecipe(
			new ItemStack(yyBlockLargeBasket, 1),
			new Object[] {
				"WWW",
				"MWM",
				"MSM",
				'W', new ItemStack(FCBetterThanWolves.fcItemWickerPiece),
				'M', new ItemStack(FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, 32767),
				'S', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767)
		});
		
		// basket
		FCRecipes.RemoveVanillaShapelessRecipe(
			new ItemStack(FCBetterThanWolves.fcItemWickerPiece, 4),
			new Object[] {new ItemStack(FCBetterThanWolves.fcBlockBasketWicker)
		});
		yyItemBarkStitching = new YYItemBarkStitching(26002);
		yyItemStitchedBark = new Item(26003)
			.SetBuoyant()
			.setUnlocalizedName("yyItemStitchedBark")
			.SetBellowsBlowDistance(2)
			.SetFilterableProperties(Item.m_iFilterable_Thin)
			.SetIncineratedInCrucible()
			.SetFurnaceBurnTime(75);
		FCRecipes.AddShapelessRecipe(
				new ItemStack(FCBetterThanWolves.fcBlockBasketWicker),
				new Object[] {
					new ItemStack(yyItemStitchedBark),
					new ItemStack(yyItemStitchedBark),
					new ItemStack(yyItemStitchedBark),
					new ItemStack(yyItemStitchedBark)
		});
		
		FCRecipes.AddShapelessRecipe(
			new ItemStack(yyItemBarkStitching, 1, YYItemBarkStitching.maxDamage-1),
			new Object[] {
				new ItemStack(FCBetterThanWolves.fcItemBark, 1, FCUtilsInventory.m_iIgnoreMetadata),
				new ItemStack(FCBetterThanWolves.fcItemBark, 1, FCUtilsInventory.m_iIgnoreMetadata),
				new ItemStack(FCBetterThanWolves.fcItemBark, 1, FCUtilsInventory.m_iIgnoreMetadata),
				new ItemStack(FCBetterThanWolves.fcItemBark, 1, FCUtilsInventory.m_iIgnoreMetadata)
		});
		
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack(FCBetterThanWolves.fcBlockBasketWicker),
			new Object[] {
				"##",
				"##",
				'#', FCBetterThanWolves.fcItemWickerPiece
		});
		
		
		// chest iron
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack(FCBetterThanWolves.fcBlockChest),
			new Object[] {
				"###",
				"# #",
				"###",
				'#', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767)
		});
		FCRecipes.AddRecipe(
			new ItemStack(FCBetterThanWolves.fcBlockChest),
			new Object[] {
				"SSS",
				"SIS",
				"SSS",
				'S', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767),
				'I', new ItemStack(Item.ingotIron, 1)
		});
		Block.replaceBlock(FCBetterThanWolves.fcHopper.blockID, YYBlockHopper.class, this, new Object[]{});
		FCRecipes.AddStokedCrucibleRecipe(
			new ItemStack(FCBetterThanWolves.fcItemNuggetIron, 6),
			new ItemStack[] {new ItemStack(FCBetterThanWolves.fcBlockChest)}
		);
		FCRecipes.AddCrucibleRecipe(
				new ItemStack(FCBetterThanWolves.fcItemNuggetIron, 6),
				new ItemStack[] {new ItemStack(FCBetterThanWolves.fcBlockChest)}
			);
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack(FCBetterThanWolves.fcHopper),
			new Object[] {
				"# #",
				"XYX",
				" Z ",
				'#', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767),
				'X', FCBetterThanWolves.fcItemGear,
				'Y', Block.pressurePlatePlanks,
				'Z', new ItemStack(FCBetterThanWolves.fcBlockWoodCornerItemStubID, 1, 32767)
		});
		FCRecipes.AddRecipe(
			new ItemStack(FCBetterThanWolves.fcHopper),
			new Object[] {
				"#C#",
				"XYX",
				" Z ",
				'#', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767),
				'X', FCBetterThanWolves.fcItemGear,
				'Y', Block.pressurePlatePlanks,
				'Z', new ItemStack(FCBetterThanWolves.fcBlockWoodCornerItemStubID, 1, 32767),
				'C', new ItemStack(FCBetterThanWolves.fcBlockChest)
		});
		Block.replaceBlock(FCBetterThanWolves.fcBlockChest.blockID, YYBlockChest.class, this, new Object[]{});
		FCRecipes.AddStokedCrucibleRecipe(
			new ItemStack(FCBetterThanWolves.fcItemNuggetIron, 6),
			new ItemStack[] {new ItemStack(FCBetterThanWolves.fcHopper)}
		);
		FCRecipes.AddCrucibleRecipe(
				new ItemStack(FCBetterThanWolves.fcItemNuggetIron, 6),
				new ItemStack[] {new ItemStack(FCBetterThanWolves.fcHopper)}
			);
		
		// hamper
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack(FCBetterThanWolves.fcBlockHamper),
			new Object[] {
				"###",
				"#P#",
				"###",
				'#', FCBetterThanWolves.fcItemWickerPiece,
				'P', Block.planks
		});
		FCRecipes.AddRecipe(
			new ItemStack(FCBetterThanWolves.fcBlockHamper),
			new Object[] {
				"###",
				"# #", 
				"###",
				'#', FCBetterThanWolves.fcItemWickerPiece
		});
		FCRecipes.AddShapelessRecipe(
			new ItemStack(FCBetterThanWolves.fcItemWickerPiece, 4),
			new Object[] {new ItemStack(FCBetterThanWolves.fcBlockHamper)}
		);
		
		// chisel
		Block.replaceBlock(Block.oreDiamond.blockID, YYBlockOreDiamond.class, this, new Object[]{});
		Block.replaceBlock(Block.oreRedstone.blockID, YYBlockOreRedstone.class, this, new Object[]{false});
		Block.replaceBlock(Block.oreRedstoneGlowing.blockID, YYBlockOreRedstone.class, this, new Object[]{false});
		Block.oreDiamond.SetChiselsEffectiveOn(false);
		Block.oreRedstone.SetChiselsEffectiveOn(false);
		
		// light
		Block.torchRedstoneActive.setLightValue(0);
		Block.blockRedstone.setLightValue(0);
		TileEntity.addMapping(YYTileEntityDynamicLightSource.class, "yyDynamicLightSource");
		
		// crops
		Block.replaceBlock(FCBetterThanWolves.fcBlockHempCrop.blockID, YYBlockHempCrop.class, this, new Object[] {});
		FCRecipes.AddRecipe(
			new ItemStack(Item.hoeStone),
			new Object[] {
				"AA",
				"BC",
				" C",
				'A', FCBetterThanWolves.fcItemStone,
				'B', FCBetterThanWolves.fcItemHempFibers,
				'C', Item.stick
		});
		FCRecipes.AddRecipe(
			new ItemStack(Item.hoeStone),
			new Object[] {
				"AA",
				"CB",
				"C ",
				'A', FCBetterThanWolves.fcItemStone,
					'B', FCBetterThanWolves.fcItemHempFibers,
				'C', Item.stick
		});
		FCRecipes.AddRecipe(
			new ItemStack(Item.hoeStone),
			new Object[] {
				"AA",
				"BC",
				" C",
				'A', FCBetterThanWolves.fcItemStone,
				'B', Item.silk,
				'C', Item.stick
		});
		FCRecipes.AddRecipe(
			new ItemStack(Item.hoeStone),
			new Object[] {
				"AA",
				"CB",
				"C ",
				'A', FCBetterThanWolves.fcItemStone,
				'B', Item.silk,
				'C', Item.stick
		});
		Item.hoeStone.setMaxDamage(12);
		
		// hardcore compass
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack(Item.compass, 1),
			new Object[] {
				" # ",
				"#X#",
				" # ",
				'#', FCBetterThanWolves.fcItemNuggetIron,
				'X', Item.redstone
		});
		FCRecipes.AddRecipe(
			new ItemStack(Item.compass, 1),
			new Object[] {
				" # ",
				"#X#",
				" # ",
				'#', Item.ingotIron,
				'X', FCBetterThanWolves.fcItemSoulUrn
		});
		
		// brick oven recovery
		yyItemBrokenBrick = new Item(26004).setUnlocalizedName("yyItemBrokenBrick");
		yyBlockHempBrickWet = new YYBlockHempBrickWet(1503);
		yyItemHempBrickWet = new YYItemHempBrickWet(26005);
		TileEntity.addMapping(YYTileEntityHempBrickWet.class, "yyBrickHempWet");
		yyBlockHempBrickDry = new YYBlockHempBrickDry(1504);
		yyItemHempBrickDry = new YYItemHempBrickDry(26006);
		TileEntity.addMapping(YYTileEntityHempBrickDry.class, "yyBrickHempDry");
		FCRecipes.AddRecipe(
			new ItemStack(yyItemHempBrickDry),
			new Object[] {
				"FBF",
				"FFF",
				"FBF",
				'B', yyItemBrokenBrick,
				'F', FCBetterThanWolves.fcItemHempFibers
		});
		
		// torch stuff
		yyBlockTorchSparklingUnlit = new YYBlockTorchSparklingUnlit(1501);
		yyBlockTorchSparklingBurning = new YYBlockTorchSparklingBurning(1502);
		TileEntity.addMapping(YYTileEntityTorchSparkling.class, "yyTorchSparkling");
		Item.itemsList[yyBlockTorchSparklingUnlit.blockID] = new YYItemBlockTorchSparklingIdle(yyBlockTorchSparklingUnlit.blockID-256);
		Item.itemsList[yyBlockTorchSparklingBurning.blockID] = new YYItemBlockTorchSparklingBurning(yyBlockTorchSparklingBurning.blockID-256);
		FCRecipes.AddShapelessRecipe(
			new ItemStack(yyBlockTorchSparklingUnlit, 8),
			new ItemStack[]{
				new ItemStack(Item.redstone),
				new ItemStack(FCBetterThanWolves.fcBlockTorchFiniteUnlit),
				new ItemStack(FCBetterThanWolves.fcBlockTorchFiniteUnlit),
				new ItemStack(FCBetterThanWolves.fcBlockTorchFiniteUnlit),
				new ItemStack(FCBetterThanWolves.fcBlockTorchFiniteUnlit),
				new ItemStack(FCBetterThanWolves.fcBlockTorchFiniteUnlit),
				new ItemStack(FCBetterThanWolves.fcBlockTorchFiniteUnlit),
				new ItemStack(FCBetterThanWolves.fcBlockTorchFiniteUnlit),
				new ItemStack(FCBetterThanWolves.fcBlockTorchFiniteUnlit)
		});
		FCRecipes.AddCauldronRecipe(
			new ItemStack(yyBlockTorchSparklingUnlit, 32),
			new ItemStack[] {
				new ItemStack(Item.redstone),
				new ItemStack(FCBetterThanWolves.fcBlockTorchFiniteUnlit, 32)
			}
		);
		
		// armor balance
		((ItemArmor)FCBetterThanWolves.fcItemArmorWoolChest).damageReduceAmount++;
		Item.plateLeather.damageReduceAmount++;
		Item.legsLeather.damageReduceAmount++;
		((ItemArmor)FCBetterThanWolves.fcItemArmorTannedChest).damageReduceAmount++;
		((ItemArmor)FCBetterThanWolves.fcItemArmorTannedLeggings).damageReduceAmount++;
		
		// yarn
		yyItemDropSpindle = new Item(26008).setUnlocalizedName("yyItemDropSpindle").SetFilterableProperties(Item.m_iFilterable_Narrow);
		yyItemYarnSpinning = new YYItemYarnSpinning(26007);
		FCRecipes.AddRecipe(
			new ItemStack(yyItemDropSpindle),
			new Object[] {
				"P",
				"S",
				'P', FCBetterThanWolves.fcItemChiselWood,
				'S', FCBetterThanWolves.fcItemStone
		});
		FCRecipes.AddShapelessRecipe(
			new ItemStack(yyItemYarnSpinning, 1, YYItemYarnSpinning.maxDamage - 1),
			new ItemStack[] {
				new ItemStack(yyItemDropSpindle),
				new ItemStack(FCBetterThanWolves.fcItemWool, 1, FCUtilsInventory.m_iIgnoreMetadata)
		});
		
		
		// totem
		yyBlockTotemSapling = new YYBlockTotemSapling(1505);
		Item.itemsList[yyBlockTotemSapling.blockID] = new ItemBlock(1505-256);
		yyBlockTotem = new YYBlockTotem(1506);
		Item.itemsList[yyBlockTotem.blockID] = new ItemBlock(1506-256);
		yyBlockTotemBottom = new YYBlockTotemBottom(1507);
		Item.itemsList[yyBlockTotemBottom.blockID] = new ItemBlock(1507-256);
		yyBlockTotemCarving = new YYBlockTotemCarving(1508);
		Item.itemsList[yyBlockTotemCarving.blockID] = new ItemBlock(1508-256);
		
		// early nether recipes
		potashRecipes();
		crucibleIronRecipes();
		
		// hardcore meat
		yyItemCookedMeat = new FCItemFood(26009, 4, 0.25F, true, "yyItemCookedMeat", true);
		yyItemSpoiledMeat = new FCItemFood(26010, 3, 0.25F, true, "yyItemSpoiledMeat", true);
		yyItemSpoiledMeat.setPotionEffect(Potion.hunger.id, FCItemFood.m_iFoodPoisioningIncreasedDuration, 0, 0.7F);
		FCRecipes.AddShapelessRecipe(
			new ItemStack( FCBetterThanWolves.fcItemMeatCured ),
			new Object[] {
				new ItemStack(yyItemSpoiledMeat),
				new ItemStack(FCBetterThanWolves.fcItemNitre
		)});
		FCRecipes.AddCampfireRecipe(yyItemSpoiledMeat.itemID, new ItemStack(yyItemCookedMeat));
		FurnaceRecipes.smelting().addSmelting(yyItemSpoiledMeat.itemID, new ItemStack(yyItemCookedMeat), 0);

		yyItemChickenDecaying = new FCItemFood(26011, FCItemFood.m_iChickenRawHungerHealed, FCItemFood.m_fChickenSaturationModifier, true, "yyItemChickenDecaying", true).SetStandardFoodPoisoningEffect();
		yyItemBeefDecaying = new FCItemFood( 26012, FCItemFood.m_iBeefRawHungerHealed, FCItemFood.m_fBeefSaturationModifier, true, "yyItemBeefDecaying", true).SetStandardFoodPoisoningEffect();
		yyItemMuttonDecaying = new FCItemFood(26013, FCItemFood.m_iMuttonRawHungerHealed, FCItemFood.m_fMuttonSaturationModifier, true, "yyItemMuttonDecaying", true).SetStandardFoodPoisoningEffect();
		yyItemFishDecaying = new FCItemFood(26014, FCItemFood.m_iFishRawHungerHealed, FCItemFood.m_fFishSaturationModifier, false, "yyItemFishDecaying").SetStandardFoodPoisoningEffect();
		yyItemPorkDecaying = new FCItemFood(26015, FCItemFood.m_iPorkChopRawHungerHealed, FCItemFood.m_fPorkChopSaturationModifier, true, "yyItemPorkDecaying", true).SetStandardFoodPoisoningEffect();
		
		FurnaceRecipes.smelting().addSmelting(yyItemPorkDecaying.itemID, new ItemStack(Item.porkCooked), 0.35F);
		FurnaceRecipes.smelting().addSmelting(yyItemBeefDecaying.itemID, new ItemStack(Item.beefCooked), 0.35F);
		FurnaceRecipes.smelting().addSmelting(yyItemChickenDecaying.itemID, new ItemStack(Item.chickenCooked), 0.35F);
		FurnaceRecipes.smelting().addSmelting(yyItemFishDecaying.itemID, new ItemStack(Item.fishCooked), 0.35F);
		FurnaceRecipes.smelting().addSmelting(yyItemMuttonDecaying.itemID, new ItemStack(FCBetterThanWolves.fcItemMuttonCooked), 0.35F);
		
		FCRecipes.AddCampfireRecipe(yyItemPorkDecaying.itemID, new ItemStack(Item.porkCooked));
		FCRecipes.AddCampfireRecipe(yyItemBeefDecaying.itemID, new ItemStack(Item.beefCooked));
		FCRecipes.AddCampfireRecipe(yyItemChickenDecaying.itemID, new ItemStack(Item.chickenCooked));
		FCRecipes.AddCampfireRecipe(yyItemFishDecaying.itemID, new ItemStack(Item.fishCooked));
		FCRecipes.AddCampfireRecipe(yyItemMuttonDecaying.itemID, new ItemStack(FCBetterThanWolves.fcItemMuttonCooked));
		
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemMeatCured), new Object[]{new ItemStack(yyItemChickenDecaying), new ItemStack(FCBetterThanWolves.fcItemNitre)});
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemMeatCured), new Object[]{new ItemStack(yyItemBeefDecaying), new ItemStack(FCBetterThanWolves.fcItemNitre)});
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemMeatCured), new Object[]{new ItemStack(yyItemMuttonDecaying), new ItemStack(FCBetterThanWolves.fcItemNitre)});
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemMeatCured), new Object[]{new ItemStack(yyItemFishDecaying), new ItemStack(FCBetterThanWolves.fcItemNitre)});
		FCRecipes.AddShapelessRecipe(new ItemStack(FCBetterThanWolves.fcItemMeatCured), new Object[]{new ItemStack(yyItemPorkDecaying), new ItemStack(FCBetterThanWolves.fcItemNitre)});
		
		FCRecipes.AddStokedCauldronRecipe(
			new ItemStack( FCBetterThanWolves.fcItemTallow, 1 ),
			new ItemStack[] {new ItemStack(yyItemPorkDecaying, 1)});
		FCRecipes.AddStokedCauldronRecipe(
			new ItemStack( FCBetterThanWolves.fcItemTallow, 1 ),
			new ItemStack[] {new ItemStack(yyItemBeefDecaying, 4)});
		FCRecipes.AddStokedCauldronRecipe(
			new ItemStack( FCBetterThanWolves.fcItemTallow, 1 ),
			new ItemStack[] {new ItemStack(yyItemMuttonDecaying, 1)});
		
		if(yyHCMeat)
		{
			Item.porkRaw.setUnlocalizedName("yyItemPorkFresh");
			Item.beefRaw.setUnlocalizedName("yyItemBeefFresh");
			Item.chickenRaw.setUnlocalizedName("yyItemChickenFresh");
			Item.fishRaw.setUnlocalizedName("yyItemFishFresh");
			FCBetterThanWolves.fcItemMuttonRaw.setUnlocalizedName("yyItemMuttonFresh");
		}
		else
		{
			YYMeatDecay.stats.clear();
		}
				
		FCAddOnHandler.LogMessage("Stuff & Things Addon Initialization Complete");
	}
	
	@Override
	public void PostInitialize() {
		Item.replaceItem(FCBetterThanWolves.fcItemPileSoulSand.itemID, YYItemPileSoulSand.class, new String[] {"Better Terrain"}, instance);
	}
	
	public void anvilRestrictRecipe(ItemStack itemStack, Object aobj[] )
	{
		YYCraftingManagerAnvil.getInstance().addRecipe(itemStack, aobj);
		FCRecipes.RemoveVanillaRecipe(itemStack, aobj);
	}
	
	public void anvilRestrictRecipes()
	{
		anvilRestrictRecipe(new ItemStack(Item.pickaxeIron), new Object[] {
				"III",
				" S ",
				" S ",
				'I', Item.ingotIron,
				'S', Item.stick
				});
		anvilRestrictRecipe(new ItemStack(FCBetterThanWolves.fcItemChiselDiamond), new Object[] {
				"X", 
				'X', FCBetterThanWolves.fcItemIngotDiamond
			});
		anvilRestrictRecipe(new ItemStack(FCBetterThanWolves.fcItemShearsDiamond), new Object[] {
				"X ",
				" X",
				'X', FCBetterThanWolves.fcItemIngotDiamond
			});
		anvilRestrictRecipe( new ItemStack( Item.hoeDiamond ), new Object[] {
				"X#", 
				" #", 
				" #",
				'#', Item.stick,
				'X', FCBetterThanWolves.fcItemIngotDiamond			
			} );
		anvilRestrictRecipe( new ItemStack( FCBetterThanWolves.fcItemDiamondPlate, 1 ), new Object[] {
				"#X#",
				" Y ",
				'#', FCBetterThanWolves.fcItemStrap, 
				'X', FCBetterThanWolves.fcItemIngotDiamond,
				'Y', FCBetterThanWolves.fcItemPadding
			} );
		anvilRestrictRecipe( new ItemStack( Item.helmetDiamond ), new Object[] {
				"XXX", 
				"XYX", 
				'X', FCBetterThanWolves.fcItemIngotDiamond,
				'Y', FCBetterThanWolves.fcItemDiamondPlate
			} ); 
		anvilRestrictRecipe( new ItemStack( Item.plateDiamond ), new Object[] {
				"Y Y", 
				"XXX", 
				"XXX", 
				'X', FCBetterThanWolves.fcItemIngotDiamond,
				'Y', FCBetterThanWolves.fcItemDiamondPlate
			} );
		anvilRestrictRecipe( new ItemStack( Item.legsDiamond ), new Object[] {
				"XXX", 
				"Y Y", 
				"Y Y", 
				'X', FCBetterThanWolves.fcItemIngotDiamond,
				'Y', FCBetterThanWolves.fcItemDiamondPlate
			} );
		anvilRestrictRecipe( new ItemStack( Item.bootsDiamond ), new Object[] {
				"X X", 
				"X X", 
				'X', FCBetterThanWolves.fcItemIngotDiamond
			} );
		anvilRestrictRecipe( new ItemStack( Item.swordDiamond ), new Object[] {
				"X", 
				"X", 
				"#",
				'#', Item.stick,
				'X', FCBetterThanWolves.fcItemIngotDiamond
			} );
		anvilRestrictRecipe( new ItemStack( Item.pickaxeDiamond ), new Object[] {
				"XXX", 
				" # ", 
				" # ",
				'#', Item.stick,
				'X', FCBetterThanWolves.fcItemIngotDiamond			
			} ); 
		anvilRestrictRecipe( new ItemStack( Item.shovelDiamond ), new Object[] {
				"X", 
				"#", 
				"#",
				'#', Item.stick,
				'X', FCBetterThanWolves.fcItemIngotDiamond			
			} ); 
	}
	
	public void reverseCE()
	{
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack( FCBetterThanWolves.fcItemWindMillBlade ),
			new Object[] {
				"###",
				"XXX",
				'#', FCBetterThanWolves.fcItemHempCloth, 
				'X', new ItemStack( FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, FCUtilsInventory.m_iIgnoreMetadata ) 
		});
		
		FCRecipes.RemoveVanillaRecipe(
			new ItemStack( FCBetterThanWolves.fcItemWindMillBlade ),
			new Object[] {
				"###",
				"XXX",
				'#', FCBetterThanWolves.fcItemHempCloth, 
				'X', Block.planks 
		});
		FCRecipes.AddRecipe(
			new ItemStack( FCBetterThanWolves.fcItemWindMillBlade ),
			new Object[] {
				"###",
				"###",
				"XXX",
				'#', FCBetterThanWolves.fcItemHempCloth, 
				'X', new ItemStack( FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, FCUtilsInventory.m_iIgnoreMetadata ) 
		});
		FCRecipes.AddRecipe(
			new ItemStack( FCBetterThanWolves.fcItemWindMillBlade ),
			new Object[] {
				"###",
				"###",
				"XXX",
				'#', FCBetterThanWolves.fcItemHempCloth,
				'X', Block.planks
		});
		
	}
	
	public void potashRecipes()
	{
		FCRecipes.AddCauldronRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( Block.wood, 1, FCUtilsInventory.m_iIgnoreMetadata ) 
			} );
		
		FCRecipes.AddCauldronRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcBloodWood, 1, FCUtilsInventory.m_iIgnoreMetadata ) 
			} );
		
		FCRecipes.AddCauldronRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( Block.planks, 6, FCUtilsInventory.m_iIgnoreMetadata ) 
			} );
		
		FCRecipes.AddCauldronRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcBlockWoodSidingItemStubID, 12, FCUtilsInventory.m_iIgnoreMetadata ),
			} );

		FCRecipes.AddCauldronRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 24, FCUtilsInventory.m_iIgnoreMetadata ),
			} );

		FCRecipes.AddCauldronRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcBlockWoodCornerItemStubID, 48, FCUtilsInventory.m_iIgnoreMetadata ),
			} );

		FCRecipes.AddCauldronRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcItemSawDust, 16 )
			} );

		FCRecipes.AddCauldronRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcItemBark, 64, FCUtilsInventory.m_iIgnoreMetadata )
			} );
		
		FCRecipes.AddCauldronRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcItemSoulDust, 16 )
			} );
		
		// remove
		
		FCCraftingManagerCauldronStoked.getInstance().RemoveRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( Block.wood, 1, FCUtilsInventory.m_iIgnoreMetadata ) 
			} );
		
		FCCraftingManagerCauldronStoked.getInstance().RemoveRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcBloodWood, 1, FCUtilsInventory.m_iIgnoreMetadata ) 
			} );
		
		FCCraftingManagerCauldronStoked.getInstance().RemoveRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( Block.planks, 6, FCUtilsInventory.m_iIgnoreMetadata ) 
			} );
		
		FCCraftingManagerCauldronStoked.getInstance().RemoveRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcBlockWoodSidingItemStubID, 12, FCUtilsInventory.m_iIgnoreMetadata ),
			} );

		FCCraftingManagerCauldronStoked.getInstance().RemoveRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 24, FCUtilsInventory.m_iIgnoreMetadata ),
			} );

		FCCraftingManagerCauldronStoked.getInstance().RemoveRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcBlockWoodCornerItemStubID, 48, FCUtilsInventory.m_iIgnoreMetadata ),
			} );

		FCCraftingManagerCauldronStoked.getInstance().RemoveRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcItemSawDust, 16 )
			} );

		FCCraftingManagerCauldronStoked.getInstance().RemoveRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcItemBark, 64, FCUtilsInventory.m_iIgnoreMetadata )
			} );
		
		FCCraftingManagerCauldronStoked.getInstance().RemoveRecipe(
				new ItemStack( FCBetterThanWolves.fcItemPotash, 1 ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcItemSoulDust, 16 )
			} );
	}
	
	public void crucibleIronRecipes()
	{
		FCRecipes.AddCrucibleRecipe( 
				//new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 4 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 2 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcItemMail )
			} );
			
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 7 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 5 ), 
			new ItemStack[] {
				new ItemStack( Item.bucketEmpty )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 7 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 5 ), 
			new ItemStack[] {
				new ItemStack( Item.bucketLava )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 7 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 5 ), 
			new ItemStack[] {
				new ItemStack( Item.bucketWater )
		} );
			
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 7 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 5 ), 
			new ItemStack[] {
				new ItemStack( Item.bucketMilk )
		} );
			
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 7 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 5 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcItemBucketCement )
		} );
			
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 3 ), 
			new ItemStack( Item.ingotIron, 2 ), 
			new ItemStack[] {
				new ItemStack( Item.pickaxeIron, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
				
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 2 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 12 ), 
			new ItemStack[] {
				new ItemStack( Item.axeIron, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
				
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 2 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 12 ), 
			new ItemStack[] {
				new ItemStack( Item.swordIron, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
					
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 2 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 6 ), 
			new ItemStack[] {
				new ItemStack( Item.hoeIron, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 1 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 6 ), 
			new ItemStack[] {
				new ItemStack( Item.shovelIron, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 5 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 30 ), 
			new ItemStack[] {
				new ItemStack( Item.helmetIron, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 8 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 48 ), 
			new ItemStack[] {
				new ItemStack( Item.plateIron, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 7 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 42 ), 
			new ItemStack[] {
				new ItemStack( Item.legsIron, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 4 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 24 ), 
			new ItemStack[] {
				new ItemStack( Item.bootsIron, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 20 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 13 ), 
			new ItemStack[] {
				new ItemStack( Item.helmetChain, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
			
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 32 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 21 ), 
			new ItemStack[] {
				new ItemStack( Item.plateChain, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 28 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 19 ), 
			new ItemStack[] {
				new ItemStack( Item.legsChain, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 16 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 11 ), 
			new ItemStack[] {
				new ItemStack( Item.bootsChain, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
			
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 4 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 3 ), 
			new ItemStack[] {
				new ItemStack( Item.compass )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			new ItemStack[] {
				//new ItemStack( Item.ingotIron, 6 ),
				//new ItemStack( Item.goldNugget, 6 ),
				new ItemStack( Item.ingotIron, 4 ),
				new ItemStack( Item.goldNugget, 4 ),
			},
			new ItemStack[] {
				new ItemStack( Item.doorIron )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 4 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 3 ), 
			new ItemStack[] {
				new ItemStack( Item.map )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 2 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 12 ), 
			new ItemStack[] {
				new ItemStack( Item.shears, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 1 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 1 ), 
			new ItemStack[] {
				new ItemStack( Block.railDetector )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 1 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 1 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcDetectorRailWood )
		} );
		
		FCRecipes.AddCrucibleRecipe(
			new ItemStack[] {
				//new ItemStack( Item.ingotIron, 1 ), 
				new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 1 ), 
				new ItemStack( FCBetterThanWolves.fcItemNuggetSteel, 3 ), 
			},
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcBlockDetectorRailSoulforgedSteel, 1 )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 1 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 6 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcPulley )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 3 ), 
			new ItemStack( Item.ingotIron, 2 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcSaw )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			new ItemStack( Item.ingotIron, 9 ), 
			new ItemStack[] {
				new ItemStack( Block.blockIron )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 5 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 30 ), 
			new ItemStack[] {
				new ItemStack( Item.minecartEmpty )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 5 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 30 ), 
			new ItemStack[] {
				new ItemStack( Item.minecartCrate )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 5 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 30 ), 
			new ItemStack[] {
				new ItemStack( Item.minecartPowered )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 3 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 1 ), 
			new ItemStack[] {
				new ItemStack( Block.rail, 2 )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 3 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 18 ), 
			new ItemStack[] {
				new ItemStack( Block.fenceIron, 8 ) // iron bars
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 7 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 42 ), 
			new ItemStack[] {
				new ItemStack( Item.cauldron )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 7 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 42 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcCauldron )
		} );
		
		FCRecipes.AddCrucibleRecipe(
			new ItemStack(FCBetterThanWolves.fcItemNuggetIron, 20),
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcItemScrew )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 6 ), 
			new ItemStack( Item.ingotIron, 4 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcBlockScrewPump )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 1 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 6 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcItemArmorGimpHelm )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 2 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 12 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcItemArmorGimpChest )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 1 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 6 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcItemArmorGimpLeggings )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 2 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 12 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcItemArmorGimpBoots )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 7 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 42 ), 
			new ItemStack[] {
				new ItemStack( Block.anvil )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 1 ), 
			new ItemStack[] {
				new ItemStack( Block.tripWireSource, 1 )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 4 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 2 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcItemChiselIron, 1, FCUtilsInventory.m_iIgnoreMetadata )
		} );
			
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 1 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 6 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcItemMetalFragment )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 54 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 36 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcBlockShovel )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
			//new ItemStack( Item.ingotIron, 11 ), 
			new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 8 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcBlockSpikeIron )
		} );
		
		FCRecipes.AddCrucibleRecipe( 
				new ItemStack( Item.ingotIron, 1  ), 
				new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 9 )
				} );
	}
	
}
