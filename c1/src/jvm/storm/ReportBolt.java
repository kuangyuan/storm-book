package storm;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.testing.TestWordSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ReportBolt extends BaseRichBolt {
  private HashMap<String, Long> counts = null;

  public void prepare(Map config, TopologyContext context, OutputCollector collector) {
    this.counts = new HashMap<String, Long>();
  }

  public void execute(Tuple tuple) {
    String word = tuple.getStringByField("word");
    Long count = tuple.getLongByField("count");
    this.counts.put(word, count);
  }

  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    
  }

  public void cleanup() {
    System.out.println("--- FINAL COUNTS ---");
    List<String> keys = new ArrayList<String>();
    keys.addAll(this.counts.keySet());
    Collections.sort(keys);
    for(String key: keys) {
      System.out.println(key + " : " + this.counts.get(key));
    }
    System.out.println("--------------");
  }
}