package ca.cmpt276.carbontracker.carbontracker;

import java.util.ArrayList;

/**
 * Created by Khang Buin on 2017-03-02.
 */

public class RouteCollection {
    private ArrayList<Route> routes = new ArrayList<Route>();

    public void add(Route route){
        routes.add(route);
    }

    public void remove(Route route){
        routes.remove(route);
    }
    public void remove(String routeName){
        for(Route route: routes){
            if(route.getName().toLowerCase() == routeName.toLowerCase()){
                remove(route);
            }
        }
    }

    public ArrayList<Route> getAllRoutes(){
        return routes;
    }

    public ArrayList<Route> findRouteWithName(String name){
        ArrayList<Route> temp = new ArrayList<Route>();
        for(Route route: routes){
            if(route.getName().toLowerCase() == name.toLowerCase()){
                temp.add(route);
            }
        }
        return temp;
    }

    public ArrayList<Route> findRouteWithCityDistance(float cityDistance){
        ArrayList<Route> temp = new ArrayList<Route>();
        for(Route route: routes){
            if(route.getCityDriveDistance() == cityDistance){
                temp.add(route);
            }
        }
        return temp;
    }
    public ArrayList<Route> findRouteWithHighwayDistance(float highwayDistance){
        ArrayList<Route> temp = new ArrayList<Route>();
        for(Route route: routes){
            if(route.getHighwayDriveDistance() == highwayDistance){
                temp.add(route);
            }
        }
        return temp;
    }
}
