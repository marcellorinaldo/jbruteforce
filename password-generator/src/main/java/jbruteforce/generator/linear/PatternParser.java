package jbruteforce.generator.linear;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class PatternParser {

    private static final char PATTERN_EXACT_TOKEN = '\"';
    
    private List<Node> nodes = new LinkedList<>();
    private Map<Integer, Node> exactNodes = new HashMap<>();
    private int parsedNodes = 0;
    
    public PatternParser(String pattern) {
        char[] tokens = pattern.toCharArray();

        for (int i=0; i<tokens.length; i++) {
            char token = tokens[i];

            if (token == PATTERN_EXACT_TOKEN) {
                do {
                    token = tokens[++i];
                    parseExactToken(token);
                } while (token != PATTERN_EXACT_TOKEN && i < tokens.length);
            }

            if (token != PATTERN_EXACT_TOKEN) {
                parseUnknownToken(token);
            }
        }
    }

    public List<Node> getUnknwonNodes() {
        return this.nodes;
    }

    public Map<Integer, Node> getExactNodes() {
        return this.exactNodes;
    }

    private void parseExactToken(char token) {
        if (token != PATTERN_EXACT_TOKEN) {
            this.exactNodes.put(this.parsedNodes, new ExactToken(token));
            this.parsedNodes++;
        }
    }

    private void parseUnknownToken(char token) {
        if (token == 'C') {
            this.nodes.add(new UpperCase());
        }

        if (token == 'c') {
            this.nodes.add(new LowerCase());
        }

        if (token == '0') {
            this.nodes.add(new Number());
        }

        this.parsedNodes++;
    }

    private class UpperCase extends Node {

        @Override
        public void reset() {
            this.value = 65;
        }

        @Override
        public boolean limitReached() {
            return this.value >= 91;
        }
    }

    private class LowerCase extends Node {

        @Override
        public void reset() {
            this.value = 97;
        }

        @Override
        public boolean limitReached() {
            return this.value >= 123;
        }
    }

    private class Number extends Node {

        @Override
        public void reset() {
            this.value = 48;
        }

        @Override
        public boolean limitReached() {
            return this.value >= 58;
        }
    }

    private class ExactToken extends Node {

        public ExactToken(char value) {
            this.value = value;
        }
        
        @Override
        public void reset() {
            // do nothing
        }

        @Override
        public void increment() {
            // do nothing
        }

        @Override
        public boolean limitReached() {
            return true;
        }
    }

}
