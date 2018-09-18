package com.autotrade.out;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.autotrade.huobi.api.ApiClient;
import com.autotrade.huobi.api.ApiException;
import com.autotrade.huobi.request.CreateOrderRequest;
import com.autotrade.huobi.request.DepthRequest;
import com.autotrade.huobi.request.IntrustOrdersDetailRequest;
import com.autotrade.huobi.response.Accounts;
import com.autotrade.huobi.response.AccountsResponse;
import com.autotrade.huobi.response.BalanceResponse;
import com.autotrade.huobi.response.BatchcancelResponse;
import com.autotrade.huobi.response.CurrencysResponse;
import com.autotrade.huobi.response.DepthResponse;
import com.autotrade.huobi.response.DetailResponse;
import com.autotrade.huobi.response.HistoryTradeResponse;
import com.autotrade.huobi.response.IntrustDetailResponse;
import com.autotrade.huobi.response.KlineResponse;
import com.autotrade.huobi.response.MatchresultsOrdersDetailResponse;
import com.autotrade.huobi.response.MergedResponse;
import com.autotrade.huobi.response.OrdersDetailResponse;
import com.autotrade.huobi.response.SymbolsResponse;
import com.autotrade.huobi.response.TimestampResponse;
import com.autotrade.huobi.response.TradeResponse;
import com.autotrade.huobi.tools.JsonUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;

public class Main {


    static final String API_KEY = "";
    static final String API_SECRET = "";
  
    
    public static void main(String[] args) {
        try {
            apiSample();
        	
        	 // create ApiClient using your api key and api secret:
//            ApiClient client = new ApiClient(API_KEY, API_SECRET);
            
            // get symbol list:
//            print(client.getSymbols());


            
        } catch (ApiException e) {
            System.err.println("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage());
            e.printStackTrace();
            
        }
    }
    
    @SuppressWarnings("rawtypes")
	static void apiSample() {
        // create ApiClient using your api key and api secret:
        ApiClient client = new ApiClient(API_KEY, API_SECRET);
        
        // get symbol list:
//        print(client.getSymbols());

        //获取 K 线
        //------------------------------------------------------ kline -------------------------------------------------------
//        KlineResponse kline = client.kline("btcusdt", "5min", "100");
//        print(kline);

        //------------------------------------------------------ merged -------------------------------------------------------

//        MergedResponse merged = client.merged("ethusdt");
//        print(merged);

        //------------------------------------------------------ depth -------------------------------------------------------

//        DepthRequest depthRequest = new DepthRequest();
//        depthRequest.setSymbol("btcusdt");
//        depthRequest.setType("step0");
//        DepthResponse depth = client.depth(depthRequest);
//        print(depth);

        //------------------------------------------------------ trade -------------------------------------------------------
//        TradeResponse trade = client.trade("ethusdt");
//        print(trade);

        //------------------------------------------------------ historyTrade -------------------------------------------------------
//        HistoryTradeResponse historyTrade = client.historyTrade("ethusdt", "20");
//        print(historyTrade);

        //------------------------------------------------------ historyTrade -------------------------------------------------------
//        DetailResponse detailTrade = client.detail("ethusdt");
//        print(detailTrade);

        //------------------------------------------------------ symbols -------------------------------------------------------
//        SymbolsResponse symbols = client.symbols("btcusdt");
//        print(symbols);

        //------------------------------------------------------ Currencys -------------------------------------------------------
//        CurrencysResponse currencys = client.currencys("btcusdt");
//        print(currencys);

        //------------------------------------------------------ Currencys -------------------------------------------------------
//        TimestampResponse timestamp = client.timestamp();
//        print(timestamp);

        //------------------------------------------------------ accounts -------------------------------------------------------
        AccountsResponse<List<Accounts>> accounts = client.accounts();
        print(accounts);

        //------------------------------------------------------ balance -------------------------------------------------------
        List<Accounts> list = accounts.getData();
        for (Accounts account : list) {
        	BalanceResponse balance = client.balance(String.valueOf(account.getId()));
        	print(balance); //spot, if more one. it's otc
		}

        Long orderId = 123L;
        if (!list.isEmpty()) {
            // find account id:
            Accounts account = list.get(0);
            long accountId = account.getId();
            // create order:
            CreateOrderRequest createOrderReq = new CreateOrderRequest();
            createOrderReq.accountId = String.valueOf(accountId);
            createOrderReq.amount = "0.02";
            createOrderReq.price = "0.1";
            createOrderReq.symbol = "eosusdt";
            createOrderReq.type = CreateOrderRequest.OrderType.BUY_LIMIT;
            createOrderReq.source = "api";

            //------------------------------------------------------ 创建订单  -------------------------------------------------------
            orderId = client.createOrder(createOrderReq);
            print(orderId);
            // place order:

            //------------------------------------------------------ 执行订单  -------------------------------------------------------
            String r = client.placeOrder(orderId);
            print(r);
        }

        //------------------------------------------------------ submitcancel 取消订单 -------------------------------------------------------

//    SubmitcancelResponse submitcancel = client.submitcancel(orderId.toString());
//    print(submitcancel);

        //------------------------------------------------------ submitcancel 批量取消订单-------------------------------------------------------
//    String[] orderList = {"727554767","727554766",""};
//    String[] orderList = {String.valueOf(orderId)};
        List<String> orderList = new ArrayList<String>();
        orderList.add(String.valueOf(orderId));
        BatchcancelResponse submitcancels = client.submitcancels(orderList);
        print(submitcancels);

        //------------------------------------------------------ ordersDetail 订单详情 -------------------------------------------------------
        OrdersDetailResponse ordersDetail = client.ordersDetail(String.valueOf(orderId));
        print(ordersDetail);

        //------------------------------------------------------ ordersDetail 已经成交的订单详情 -------------------------------------------------------
//    String.valueOf(orderId)
        MatchresultsOrdersDetailResponse matchresults = client.matchresults("714746923");
        print(ordersDetail);

        //------------------------------------------------------ ordersDetail 已经成交的订单详情 -------------------------------------------------------
//    String.valueOf(orderId)
        IntrustOrdersDetailRequest req = new IntrustOrdersDetailRequest();
        req.symbol = "btcusdt";
        req.types = IntrustOrdersDetailRequest.OrderType.BUY_LIMIT;
//    req.startDate = "2018-01-01";
//    req.endDate = "2018-01-14";
        req.states = IntrustOrdersDetailRequest.OrderStates.FILLED;
//    req.from = "";
//    req.direct = "";
//    req.size = "";


//    public String symbol;	   //true	string	交易对		btcusdt, bccbtc, rcneth ...
//    public String types;	   //false	string	查询的订单类型组合，使用','分割		buy-market：市价买, sell-market：市价卖, buy-limit：限价买, sell-limit：限价卖
//    public String startDate;   //false	string	查询开始日期, 日期格式yyyy-mm-dd
//    public String endDate;	   //false	string	查询结束日期, 日期格式yyyy-mm-dd
//    public String states;	   //true	string	查询的订单状态组合，使用','分割		pre-submitted 准备提交, submitted 已提交, partial-filled 部分成交,
//    // partial-canceled 部分成交撤销, filled 完全成交, canceled 已撤销
//    public String from;	       //false	string	查询起始 ID
//    public String direct;	   //false	string	查询方向		prev 向前，next 向后
//    public String size;	       //false	string	查询记录大小


        //------------------------------------------------------ order 查询当前委托、历史委托 -------------------------------------------------------

        IntrustDetailResponse intrustDetail = client.intrustOrdersDetail(req);
        print(intrustDetail);


//    // get accounts:
//    List<Account> accounts1 = client.getAccounts();
//    print(accounts1);

    }

    static void print(Object obj) {
        try {
            System.out.println(JsonUtil.writeValue(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
