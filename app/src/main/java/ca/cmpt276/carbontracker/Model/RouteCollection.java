package ca.cmpt276.carbontracker.Model;

import java.util.ArrayList;
import java.util.Iterator;

public class RouteCollection implements Iterable<Route>{
    private ArrayList<Route> routes = new ArrayList<Route>();

    public void add(Route route){
        routes.add(route);
    }

    public void add(String name, float cityDriveDistance, float highwayDriveDistance) {
        routes.add(new Route(name, cityDriveDistance, highwayDriveDistance));
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

    public void remove(int index) {
        routes.remove(index);
    }

    public void editName(int index, String newName) {
        routes.get(index).setName(newName);
    }

    public void editCityDistance(int index, float cityDistance) {
        routes.get(index).setCityDriveDistance(cityDistance);
    }

    public void editHighwayDistance(int index, float highwayDistance) {
        routes.get(index).setHighwayDriveDistance(highwayDistance);
    }

    public Route getRoute(int index){
        return routes.get(index);
    }

    public RouteCollection findRouteWithName(String name){
        RouteCollection temp = new RouteCollection();
        for(Route route: routes){
            if(route.getName().toLowerCase() == name.toLowerCase()){
                temp.add(route);
            }
        }
        return temp;
    }

    public RouteCollection findRouteWithCityDistance(float cityDistance){
        RouteCollection temp = new RouteCollection();
        for(Route route: routes){
            if(route.getCityDriveDistance() == cityDistance){
                temp.add(route);
            }
        }
        return temp;
    }
    public RouteCollection findRouteWithHighwayDistance(float highwayDistance){
        RouteCollection temp = new RouteCollection();
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
