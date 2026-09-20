package github.snomfish.domain.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.snomfish.functionality.copy.DeepCopyable;

import static github.snomfish.domain.type.TypeId.*;

public class TypeChart implements DeepCopyable<TypeChart> {
    

    private Map<TypeChartKey, Double> chart = new HashMap<>();


    private TypeChart(Map<TypeChartKey, Double> chart) {
        this.chart = chart;
    }
    @Override 
    public TypeChart deepCopy() {
        Map<TypeChartKey, Double> copy = new HashMap<>();

        for (TypeChartKey key : chart.keySet()) {
            Double value = chart.get(key);
            copy.put(key, value);
        }

        return new TypeChart(copy);
    }


    public static TypeChart get() {
        return new TypeChart();
    }


    public double getModifier(TypeId moveType, List<TypeId> targetTypes) {
        double modifier = 1.0;
        for (TypeId targetType : targetTypes) {
            modifier *= get(moveType, targetType);
        }
        return modifier;
    }
    public double get(TypeId attackingType, TypeId defendingType) {
        return chart.get(new TypeChartKey(attackingType, defendingType));
    }
    public void set(TypeId attackingType, TypeId defendingType, double multiplier) {
        chart.put(new TypeChartKey(attackingType, defendingType), multiplier);
    }
    public TypeChart() {
        for (TypeId attackingType : TypeId.values()) {
            for (TypeId defendingType : TypeId.values()) {
                set(attackingType, defendingType, 1.0);
            }
        }

        set(FIRE, FIRE, 0.5);
        set(FIRE, WATER, 0.5);
        set(FIRE, PLANT, 2.0);
        set(FIRE, LIGHT, 0.5);
        set(FIRE, DARK, 2.0);
        set(FIRE, ICE, 2.0);
        set(FIRE, METAL, 2.0);
        set(FIRE, ANCIENT, 0.5);
        
        set(WATER, FIRE, 2.0);
        set(WATER, WATER, 0.5);
        set(WATER, PLANT, 0.5);
        set(WATER, ELECTRIC, 2.0);
        set(WATER, EARTH, 2.0);
        set(WATER, ANCIENT, 0.5);
        
        set(PLANT, FIRE, 0.5);
        set(PLANT, WATER, 2.0);
        set(PLANT, PLANT, 0.5);
        set(PLANT, BUG, 0.5);
        set(PLANT, EARTH, 2.0);
        set(PLANT, TOXIC, 0.5);
        set(PLANT, ANCIENT, 0.5);

        set(LIGHT, PLANT, 0.0);
        set(LIGHT, LIGHT, 0.5);
        set(LIGHT, DARK, 2.0);
        set(LIGHT, ANCIENT, 2.0);
        set(LIGHT, SPIRIT, 0.5);
        set(LIGHT, MIND, 0.5);
        
        set(DARK, PLANT, 2.0);
        set(DARK, LIGHT, 2.0);
        set(DARK, DARK, 0.5);
        set(DARK, BUG, 0.5);
        set(DARK, SPIRIT, 0.5);
        set(DARK, MIND, 2.0);
        
        set(ICE, FIRE, 0.5);
        set(ICE, WATER, 0.5);
        set(ICE, PLANT, 2.0);
        set(ICE, ICE, 0.5);
        set(ICE, AIR, 2.0);
        set(ICE, BUG, 2.0);
        set(ICE, EARTH, 2.0);
        set(ICE, METAL, 0.5);
        set(ICE, BRAWLER, 0.5);
        
        set(ELECTRIC, WATER, 2.0);
        set(ELECTRIC, PLANT, 0.5);
        set(ELECTRIC, ELECTRIC, 0.5);
        set(ELECTRIC, AIR, 2.0);
        set(ELECTRIC, EARTH, 0.0);
        set(ELECTRIC, METAL, 2.0);
        set(ELECTRIC, ANCIENT, 0.5);
        set(ELECTRIC, SPIRIT, 0.5);
        
        set(AIR, FIRE, 2.0);
        set(AIR, ICE, 0.5);
        set(AIR, ELECTRIC, 0.5);
        set(AIR, BUG, 2.0);
        set(AIR, METAL, 0.5);
        set(AIR, BRAWLER, 2.0);
        
        set(BUG, FIRE, 0.5);
        set(BUG, PLANT, 2.0);
        set(BUG, DARK, 2.0);
        set(BUG, AIR, 0.5);
        set(BUG, BUG, 0.5);
        set(BUG, METAL, 0.5);
        set(BUG, BRAWLER, 0.5);
        set(BUG, MIND, 2.0);
        
        set(EARTH, FIRE, 2.0);
        set(EARTH, PLANT, 0.5);
        set(EARTH, ELECTRIC, 2.0);
        set(EARTH, AIR, 0.0);
        set(EARTH, BUG, 0.5);
        set(EARTH, TOXIC, 2.0);
        set(EARTH, METAL, 2.0);
        
        set(TOXIC, WATER, 2.0);
        set(TOXIC, PLANT, 2.0);
        set(TOXIC, EARTH, 0.5);
        set(TOXIC, TOXIC, 0.5);
        set(TOXIC, SPIRIT, 0.5);
        set(TOXIC, BRAWLER, 2.0);

        set(METAL, FIRE, 0.5);
        set(METAL, WATER, 0.5);
        set(METAL, LIGHT, 2.0);
        set(METAL, ICE, 2.0);
        set(METAL, ELECTRIC, 0.5);
        set(METAL, METAL, 0.5);
        set(METAL, ANCIENT, 2.0);

        set(ANCIENT, LIGHT, 0.5);
        set(ANCIENT, METAL, 0.5);
        set(ANCIENT, ANCIENT, 2.0);
        set(ANCIENT, SPIRIT, 2.0);
        
        set(SPIRIT, LIGHT, 0.0);
        set(SPIRIT, DARK, 0.5);
        set(SPIRIT, ANCIENT, 0.5);
        set(SPIRIT, SPIRIT, 2.0);
        set(SPIRIT, BRAWLER, 2.0);
        set(SPIRIT, MIND, 2.0);
        
        set(BRAWLER, ICE, 2.0);
        set(BRAWLER, AIR, 0.5);
        set(BRAWLER, BUG, 2.0);
        set(BRAWLER, TOXIC, 0.5);
        set(BRAWLER, METAL, 2.0);
        set(BRAWLER, SPIRIT, 0.0);
        set(BRAWLER, MIND, 0.5);
        set(BRAWLER, SIMPLE, 2.0);
        
        set(MIND, DARK, 0.5);
        set(MIND, TOXIC, 2.0);
        set(MIND, METAL, 0.5);
        set(MIND, BRAWLER, 2.0);
        set(MIND, MIND, 0.5);
        set(MIND, SIMPLE, 2.0);

        set(SIMPLE, ICE, 0.5);
        set(SIMPLE, METAL, 0.5);
        set(SIMPLE, SPIRIT, 0.5);
    }
}
