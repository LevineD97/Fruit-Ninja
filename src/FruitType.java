public enum FruitType {Apple, Mango, Pineapple, Strawberry, Watermelon;
   
    public static FruitType getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}