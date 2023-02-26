package jbruteforce.generator.linear;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import jbruteforce.api.PasswordGenerator;

public class LinearPasswordGenerator implements PasswordGenerator {

    private Map<Integer, Node> exactNodes = new HashMap<>();
    private List<String> passwords = new LinkedList<>();

    @Override
    public void setPattern(String pattern) {
        PatternParser parser = new PatternParser(pattern);
        this.exactNodes = parser.getExactNodes();
        generator(parser.getUnknwonNodes(), 0);
    }

    @Override
    public Optional<String> nextPassword() {
        if (!this.passwords.isEmpty()) {
            String password = passwords.remove(passwords.size() - 1);
            return Optional.of(completePasswordWithExactTokens(password));
        }

        return Optional.empty();
    }

    private String completePasswordWithExactTokens(String password) {
        for (Entry<Integer, Node> entry : this.exactNodes.entrySet()) {
            password = addChar(password, entry.getValue().get(), entry.getKey());
        }
        return password;
    }

    private String addChar(String input, char toAdd, int position) {
        return input.substring(0, position) + toAdd + input.substring(position);
    }

    private void generator(List<Node> previousList, int index) {
        if (index < previousList.size()) {
            List<Node> currentList = new LinkedList<>(previousList);

            char[] password = new char[currentList.size()];
            for (int i=0; i<currentList.size(); i++) {
                password[i] = currentList.get(i).get();
            }

            Node current = currentList.get(index);
            while (!current.limitReached()) {
                generator(currentList, index + 1);

                password[index] = current.get();
                current.increment();
                this.passwords.add(new String(password));
            }
            current.reset();
        }
    }
}
