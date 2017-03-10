package ca.cmpt276.carbontracker.Model;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * Keep a collection of Route objects and its related information
 */
public class RouteCollection implements Iterable<Route>{
    private ArrayList<Route> routes = new ArrayList<Route>();

    public void add(Route route){
        routes.add(route);
    }

    public void remove(Route route){
        routes.remove(route);
    }


    public void remove(String routeName){
        for(Route route: routes){
            if(route.getName().toLowerCase().equals(routeName.toLowerCase())){
                remove(route);
            }
        }
    }

    public void editRoute(String orignalName, String name, float cityDriveDistance, float highwayDriveDistance) {
        for( Route route : this.routes){
            if (route.getName().toLowerCase().equals(orignalName.toLowerCase()))
            {
                route.setName(name);
                route.setCityDriveDistance(cityDriveDistance);
                route.setHighwayDriveDistance(highwayDriveDistance);
            }
        }
    }

    public Route getRoute(int index){
        return routes.get(index);
    }

    public ArrayList<Route> getAllRoutes(){
        return routes;
    }

    public ArrayList<Route> findRouteWithName(String namE){
        ArrayList<Route> temp = new ArrayList<Route>();
        for(Route route: routes){
            if(route.getName().toLowerCase().equals(namE.toLowerCase())){
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
    @Override
    public Iterator<Route> iterator() {
        return routes.iterator();
    }
}
