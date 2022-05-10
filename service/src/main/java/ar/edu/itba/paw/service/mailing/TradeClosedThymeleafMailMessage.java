package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.context.Context;

public class TradeClosedThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "TradeClosed";

    private String username;
    private String buyer;
    private String quantity;
    private String coinCode;
    private String wallet;
    private String buyerEmail;
    private String tradeCode;

    public TradeClosedThymeleafMailMessage(String from, String to) {
        super(from, to, template);
    }
    public TradeClosedThymeleafMailMessage(MailMessage mailMessage) {
        super(mailMessage, template);
    }


    public void setParameters(String username, String coinCode, String quantity, String buyer, String wallet, String buyerMail, String tradeCode) {
        this.username = username;
        this.coinCode = coinCode;
        this.quantity = quantity;
        this.buyer = buyer;
        this.wallet = wallet;
        this.buyerEmail = buyerMail;
        this.tradeCode = tradeCode;
    }

    @Override
    protected Context getContext() {

        if ( username == null || coinCode == null || quantity == null || buyer == null || wallet == null || buyerEmail == null || tradeCode == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context();
        context.setVariable("staticTitle", "¡Trade Finalizada!");
        context.setVariable("staticHello", "Hola");
        context.setVariable("staticTradeWith", "La trade con");
        context.setVariable("staticFor", "por");
        context.setVariable("staticHasFinished", "ha finalizado.");
        context.setVariable("staticTransferToWallet", "Deberás transferir el monto acordado a la wallet");
        context.setVariable("staticWriteToEmail", "o escribirle a su mail");
        context.setVariable("staticDetailsWithLink", "Puedes ver los detalles del recibo a través de tu cuenta o entrando al siguiente link");
        context.setVariable("staticReceipt", "Recibo");
        context.setVariable("staticNeedMoreHelp", "¿Necesitas más ayuda?");
        context.setVariable("staticWriteUs", "Escríbenos");


        context.setVariable("username", username);
        context.setVariable("coinCode", coinCode);
        context.setVariable("quantity", quantity);
        context.setVariable("buyer", buyer);
        context.setVariable("wallet", wallet);
        context.setVariable("buyerEmail", buyerEmail);
        context.setVariable("tradeCode", tradeCode);
        return context;
    }
}
