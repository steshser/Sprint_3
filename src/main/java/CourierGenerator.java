public class CourierGenerator {

    public static Courier getNewValidCourier() {
        return new Courier("steshser", "steshser1234", "Sergey");
    }

    public static Courier getCourierWithoutLogin(){
        return new Courier(null, "steshser1234", "Sergey");
    }

    public static Courier getCourierWithoutPassword(){
        return new Courier("steshser", null, "Sergey");
    }

    public static Courier getCourierWithoutName(){
        return new Courier("steshser", "steshser1234", null);
    }

}
