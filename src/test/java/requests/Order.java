package requests;

public class Order {

    private String[] ingredients;

    //Конструктор
    public Order(String[] ingredients) {
        this.ingredients = ingredients;
    }


    //Геттер и сеттер
    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
