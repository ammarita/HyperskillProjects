package machine;

import java.util.Scanner;

enum State {
    READY,
    CHOOSING_COFFEE,
    FILLING_WATER,
    FILLING_MILK,
    FILLING_BEANS,
    FILLING_CUPS,
    SHUTTING_DOWN
}

public class CoffeeMachine {

    private int waterIn;
    private int milkIn;
    private int beansIn;
    private int cupsIn;
    private int moneyIn;
    private String input;
    private State state = State.READY;

    public CoffeeMachine(int waterIn, int milkIn, int beansIn, int cupsIn, int moneyIn) {
        this.waterIn = waterIn;
        this.milkIn = milkIn;
        this.beansIn = beansIn;
        this.cupsIn = cupsIn;
        this.moneyIn = moneyIn;
    }

    public State getState() {
        return this.state;
    }

    private void start() {
        machineReady();
    }

    private void machineReady() {
        this.state = State.READY;
        System.out.print("Write action (buy, fill, take, remaining, exit): ");
    }

    private void readInput(String input) {
        this.input = input;

        if (getState() == State.READY) {
            switch (input) {
                case "buy":
                    buyCoffee();
                    break;
                case "fill":
                    fillMachine();
                    break;
                case "take":
                    takeMoney();
                    break;
                case "remaining":
                    printRemaining();
                    break;
                case "exit":
                    stop();
                    break;
                default:
                    System.out.println("Unknown command");
                    machineReady();
                    break;
            }
        } else if (getState() == State.CHOOSING_COFFEE) {
            switch (input) {
                case "1":
                    checkResources(250, 0, 16, 4);
                    break;
                case "2":
                    checkResources(350, 75, 20, 7);
                    break;
                case "3":
                    checkResources(200, 100, 12, 6);
                    break;
                case "back":
                    machineReady();
                    break;
                default:
                    System.out.println("Unknown command");
                    buyCoffee();
                    break;
            }
        } else if (getState() == State.FILLING_WATER || getState() == State.FILLING_MILK || getState() == State.FILLING_BEANS || getState() == State.FILLING_CUPS) {
            fillMachine();
        }
    }

    private void buyCoffee() {
        this.state = State.CHOOSING_COFFEE;
        System.out.print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
    }

    private void checkResources(int waterNeeded, int milkNeeded, int beansNeeded, int price) {
        if(waterIn >= waterNeeded && milkIn >= milkNeeded && beansIn >= beansNeeded && cupsIn >0) {
            System.out.println("I have enough resources, making you a coffee!");
            waterIn -= waterNeeded;
            milkIn -= milkNeeded;
            beansIn -= beansNeeded;
            cupsIn--;
            moneyIn += price;
        } else if (waterIn < waterNeeded) {
            System.out.println("Sorry, not enough water!");
        } else if (milkIn < milkNeeded) {
            System.out.println("Sorry, not enough milk!");
        } else if (beansIn < beansNeeded) {
            System.out.println("Sorry, not enough beans!");
        } else if (cupsIn < 1) {
            System.out.println("Sorry, not enough cups");
        }
        machineReady();
    }

    private void fillMachine() {
        switch (getState()) {
            case READY:
                System.out.print("Write how many ml of water do you want to add: ");
                this.state = State.FILLING_WATER;
                break;
            case FILLING_WATER:
                this.waterIn += Integer.parseInt(this.input);
                System.out.print("Write how many ml of milk do you want to add: ");
                this.state = State.FILLING_MILK;
                break;
            case FILLING_MILK:
                this.milkIn += Integer.parseInt(this.input);
                System.out.print("Write how many grams of coffee beans do you want to add: ");
                this.state = State.FILLING_BEANS;
                break;
            case FILLING_BEANS:
                this.beansIn += Integer.parseInt(this.input);
                System.out.print("Write how many disposable cups of coffee do you want to add: ");
                this.state = State.FILLING_CUPS;
                break;
            case FILLING_CUPS:
                this.cupsIn += Integer.parseInt(this.input);
                machineReady();
                break;
            default:
                System.out.println("Unknown command");
                machineReady();
                break;
        }
    }

    private void takeMoney() {
        System.out.println("I gave you $" + moneyIn);
        moneyIn = 0;
        machineReady();
    }

    private void printRemaining() {
        System.out.println("The coffee machine has:");
        System.out.println(waterIn + " ml of water");
        System.out.println(milkIn + " ml of milk");
        System.out.println(beansIn + " g of coffee beans");
        System.out.println(cupsIn + " disposable cups");
        System.out.println("$" + moneyIn + " of money");
        machineReady();
    }

    private void stop() {
        System.out.println("Shutting down");
        this.state = State.SHUTTING_DOWN;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550);
        coffeeMachine.start();

        while (coffeeMachine.getState() != State.SHUTTING_DOWN){
            coffeeMachine.readInput(scanner.next());
        }
    }//end main
}