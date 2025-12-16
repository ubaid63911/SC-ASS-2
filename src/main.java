public class main {
    public static void main(String[] args) {
 
        SettingsLoader settingsLoader = new SettingsLoader();
        settingsLoader.loadSettings("settings.conf");
        UserType userType = settingsLoader.getUserType();
        PaymentType paymentType = settingsLoader.getPaymentType();

        FileService fileService = new FileService();
        LoggerService logger = new LoggerService();
        DiscountService discountService = new DiscountService();
        OrderReportPrinter printer = new OrderReportPrinter();
        NotificationService notifier = new NotificationService();
        ShippingService shippingService = new ShippingService();

        OrderService orderService = new OrderService(userType, paymentType,
                discountService, fileService, logger, printer);
        OrderRequest request = new OrderRequest("Ali",101,2,999.99,"Karachi",true,"Handle carefully",new OrderDate(12, 11, 2025)
        );

        try {
            double total = orderService.processOrder(request);
            System.out.printf("Order processed. Final total = %.2f%n", total);

            int shippingCost = shippingService.calculateShipping(35);
            System.out.println("Shipping cost: " + shippingCost);

            logger.exportLogs("logs.txt", fileService);

            notifier.notifyUser(request.getUserName(), "Your order has been processed successfully!");

        } catch (Exception e) {
            System.out.println("Failed to process order: " + e.getMessage());

        }
    }
}

