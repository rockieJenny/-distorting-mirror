package rsg.mailchimp.api.campaigns;

import java.util.Map;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class ECommOrders implements RPCStructConverter {
    public String email;
    public OrderDetails[] lines;
    public String orderDate;
    public String orderId;
    public Double orderTotal;
    public Double shipTotal;
    public String storeId;
    public String storeName;
    public Double taxTotal;

    public class OrderDetails implements RPCStructConverter {
        public String category;
        public Double itemCost;
        public String product;
        public String quantity;

        public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
            Utils.populateObjectFromRPCStruct(this, struct, true, new String[0]);
        }
    }

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        int i = 0;
        Utils.populateObjectFromRPCStruct(this, struct, true, new String[0]);
        Object linesObj = struct.get("lines");
        if (linesObj instanceof Object[]) {
            Object[] lines = (Object[]) linesObj;
            this.lines = new OrderDetails[lines.length];
            int i2 = 0;
            int length = lines.length;
            while (i < length) {
                Object line = lines[i];
                this.lines[i2] = new OrderDetails();
                this.lines[i2].populateFromRPCStruct(null, (Map) line);
                i2++;
                i++;
            }
        }
    }
}
