package pchromic.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pchromic.domain.Report;
import pchromic.service.OrderService;
import pchromic.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private
    OrderService service;

    @Override
    public Report setOrderReports(String clientId){
        String orderAmount;
        String ordersValue;
        String ordersAvgValue;
        if (clientId.isEmpty()){
            orderAmount = service.getTotalAmountOfOrders().toString();
            ordersValue = service.getTotalOrdersValue().toString();
            ordersAvgValue = service.getAverageValueOfOrder().toString();

        }
        else {
            orderAmount = service.getTotalAmountOfOrdersForCustomer( clientId).toString();
            ordersValue = service.getTotalOrdersValueForCustomer( clientId).toString();
            ordersAvgValue = service.getAverageValueOfOrderForCustomer( clientId).toString();
        }
        return  new Report(orderAmount,ordersValue,ordersAvgValue);
    }


}
