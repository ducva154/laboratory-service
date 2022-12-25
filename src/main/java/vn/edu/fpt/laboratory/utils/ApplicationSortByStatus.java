package vn.edu.fpt.laboratory.utils;

import vn.edu.fpt.laboratory.constant.ApplicationStatusEnum;
import vn.edu.fpt.laboratory.entity.Application;

import java.util.Comparator;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 25/12/2022 - 15:10
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public class ApplicationSortByStatus implements Comparator<Application> {

    @Override
    public int compare(Application o1, Application o2) {
        Integer o1Order = getStatusOrder(o1.getStatus());
        Integer o2Order = getStatusOrder(o2.getStatus());

        return o1Order - o2Order;
    }

    private Integer getStatusOrder(ApplicationStatusEnum status){
        if(status == ApplicationStatusEnum.WAITING_FOR_APPROVE){
            return 2;
        }else{
            return 1;
        }

    }
}
