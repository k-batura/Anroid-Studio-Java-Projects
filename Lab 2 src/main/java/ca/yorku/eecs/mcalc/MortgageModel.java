package ca.yorku.eecs.mcalc;

public class MortgageModel
{
    private double principle;
    private double interest;
    private int amortization;

    public MortgageModel(String p, String a, String i)
    {
        this.principle = Double.parseDouble(p);
        this.amortization = Integer.parseInt(a);
        this.interest = Double.parseDouble(i);
    }

    public String computePayment()
    {
        double index = ((this.interest/1200) * this.principle) / (1 - (Math.pow((1+(this.interest/1200)),(-(this.amortization*12)))));
        String result = String.format("$%,.2f", index);
        return result;
    }

    public static void main(String[] args)
    {
        MortgageModel myModel = new MortgageModel("700000", "25", "2.75");
        System.out.println(myModel.computePayment());

        myModel = new MortgageModel("300000", "20", "4.50");
        System.out.println(myModel.computePayment());
    }
}
