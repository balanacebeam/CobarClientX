package io.pddl.table.model;

import java.util.List;

import io.pddl.table.LogicTableStrategy;

public class LogicTableStrategyConfig {
	
	private List<String> columns;
	
	private LogicTableStrategy strategy;
	
	public void setColumns(List<String> columns){
		this.columns= columns;
	}
	
	public List<String> getColumns(){
		return columns;
	}
	
	public void setStrategy(LogicTableStrategy strategy){
		this.strategy= strategy;
	}
	
	public LogicTableStrategy getStrategy(){
		return strategy;
	}
}