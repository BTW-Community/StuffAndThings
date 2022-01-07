package net.minecraft.src;

public class YYUtilsPenalties
{
	public static int[][] healthPenaltyLevels =
	{
		{17,	20,	22,	24,	27,	30,	32,	34,	37,	39,	41,	44,	46,	48,	50,	52},
		{14,	15,	17,	19,	21,	22,	24,	26,	28,	30,	32,	34,	36,	38,	40,	42},
		{10,	11,	12,	14,	15,	17,	18,	20,	21,	23,	24,	26,	27,	29,	30,	32},
		{7,		7,	8,	9,	10,	11,	12,	13,	14,	15,	16,	17,	18,	19,	20,	21},
		{2,		3,	3,	4,	4,	5,	5,	6,	7,	7,	8,	8,	9,	9,	10,	10}
	};
	
	public static int[][] hungerPenaltyLevels =
	{
		{24,	25,	26,	27,	28,	30,	31,	32,	33,	35,	36},
		{18,	18,	19,	20,	21,	22,	23,	24,	25,	26,	27},
		{12,	12,	13,	13,	14,	15,	15,	16,	17,	18,	18},
		{6,		6,	6,	7,	7,	7,	8,	8,	8,	9,	9}
	};
	
	public static int[][] fatPenaltyLevels =
	{
		{12,	13,	14,	16,	17,	18,	20,	22,	23,	24,	26},
		{14,	16,	18,	19,	20,	22,	24,	25,	26,	28,	30},
		{16,	18,	20,	22,	24,	26,	28,	29,	30,	32,	34},
		{18,	20,	22,	24,	26,	28,	30,	32,	34,	36,	38}
	};
	
	public static int getHealthPenaltyLevel(int currentHealth, int maxHealth)
	{
		int column = (maxHealth-35)/7;
		if(column<0 || column>15) 
		{
			System.out.println("max health is out of range! - " + maxHealth);
			return 0;
		}
		for(int penaltyLevel=0; penaltyLevel<5; penaltyLevel++)
		{
			if(currentHealth > healthPenaltyLevels[penaltyLevel][column])
			{
				return penaltyLevel;
			}
		}
		return 5;
	}
	
	public static int getHungerPenaltyLevel(int currentFood, int maxFood)
	{
		int column = (maxFood-60)/6;
		for(int penaltyLevel=0; penaltyLevel<4; penaltyLevel++)
		{
			if(currentFood > hungerPenaltyLevels[penaltyLevel][column])
			{
				return penaltyLevel;
			}
		}
		return 4;
	}
	
	public static int getFatPenaltyLevel(int currentFat, int maxFood)
	{
		int column = (maxFood-60)/6;
		for(int penaltyLevel=0; penaltyLevel<4; penaltyLevel++)
		{
			if(currentFat <= fatPenaltyLevels[penaltyLevel][column])
			{
				return penaltyLevel;
			}
		}
		return 4;
	}
}
