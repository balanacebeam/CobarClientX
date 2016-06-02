package io.pddl.router.table.strategy.support;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import io.pddl.exception.ShardingTableException;
import io.pddl.router.table.LogicTable;
import io.pddl.router.table.strategy.LogicTableStrategy;
import io.pddl.router.table.value.ShardingCollectionValue;
import io.pddl.router.table.value.ShardingRangeValue;
import io.pddl.router.table.value.ShardingSingleValue;
import io.pddl.router.table.value.ShardingValue;

public abstract class AbstractSingleLogicTableStrategy<T extends Comparable<?>> implements LogicTableStrategy{

	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> doSharding(LogicTable table, List<ShardingValue<?>> shardingValues) {
		if(CollectionUtils.isEmpty(shardingValues)){
			return table.getPostfixes();
		}
		if(shardingValues.size()> 1){
			throw new ShardingTableException("not support multiple table strategy");
		}
		ShardingValue<T> shardingValue= (ShardingValue<T>)shardingValues.get(0);
		Set<String> result= new HashSet<String>();
		String column= shardingValue.getColumn();
		if(shardingValue instanceof ShardingSingleValue){
			ShardingSingleValue<T> singleValue= (ShardingSingleValue<T>)shardingValue;
			T value= singleValue.getSingleValue();
			result.add(doEqualSharding(table,column,value));
		}
		else if(shardingValue instanceof ShardingCollectionValue){
			ShardingCollectionValue<?> collectionValue= (ShardingCollectionValue<?>)shardingValue;
			List<T> value= (List<T>)collectionValue.getCollectionValue();
			result.addAll(doInSharding(table,column,value));
		}
		else if(shardingValue instanceof ShardingRangeValue){
			ShardingRangeValue<T> rangeValue= (ShardingRangeValue<T>)shardingValue;
			T lower= rangeValue.getLower();
			T upper= rangeValue.getUpper();
			result.addAll(doBetweenSharding(table,column,lower,upper));
		}
		return result;
	}
	
	public abstract String doEqualSharding(LogicTable table, String column,T value);
	
	public abstract Collection<String> doInSharding(LogicTable table, String column,List<T> values);
	
	public abstract Collection<String> doBetweenSharding(LogicTable table, String column,T lower,T upper);
}