package com.loki.iam;

import com.google.common.base.Splitter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 算法参考: https://www.geeksforgeeks.org/longest-prefix-matching-a-trie-based-solution-in-java/
 *
 * @author Loki
 */
public class PathTrie {

  private static final String PATH_SPLIT_SYMBOL = "/";
  private static final String ONE_ASTERISK_SYMBOL = "*";
  private static final String TWO_ASTERISK_SYMBOL = "**";
  private TrieNode root;

  public PathTrie() {
    root = new TrieNode();
  }

  private static List<String> getPathSegments(String path) {
    return Splitter.on(PATH_SPLIT_SYMBOL)
        .splitToStream(path)
        .filter(it -> !it.isEmpty())
        .collect(Collectors.toList());
  }

  // Method to insert a new word to Trie
  public void insert(String path) {

    // Find length of the given word
    final List<String> parts = getPathSegments(path);
    int length = parts.size();
    TrieNode crawl = root;

    // Traverse through all characters of given word
    for (int level = 0; level < length; level++) {
      Map<String, TrieNode> child = crawl.getChildren();
      String part = parts.get(level);
      // 如果是 **，后面的路径节点不用再插入了
      if (TWO_ASTERISK_SYMBOL.equals(crawl.name)) {
        return;
      }

      // If there is already a child for current character of given word
      if (child.containsKey(part)) {
        crawl = child.get(part);
      }
      // Else create a child
      else {
        TrieNode temp = new TrieNode(part);
        child.put(part, temp);
        crawl = temp;
      }
    }

    // Set isEnd true for last character
    crawl.setEnd(true);
  }

  /**
   * The main method that finds out the longest string 'path'
   *
   * @param path
   * @return
   */
  public String getMatchingPrefix(String path) {
    // Initialize resultant string
    String result = "";

    // Find length of the given word
    final List<String> parts = getPathSegments(path);
    int length = parts.size();

    // Initialize reference to traverse through Trie
    TrieNode crawl = root;

    // Iterate through all characters of input string 'str' and traverse
    // down the Trie
    int level, prevMatch = 0;
    for (level = 0; level < length; level++) {
      // Find current character of str
      String part = parts.get(level);

      // HashMap of current Trie node to traverse down
      Map<String, TrieNode> child = crawl.getChildren();

      // See if there is a Trie edge for the current character
      // 1. 精确匹配
      if (!child.containsKey(part)) {
        // 2. 查找"*"
        part = ONE_ASTERISK_SYMBOL;
        if (!child.containsKey(part)) {
          // 3. 查找 "**"
          part = TWO_ASTERISK_SYMBOL;
          if (!child.containsKey(part)) {
            break;
          }
        }
      }
      // Update result
      result += "/" + part;
      // Update crawl to move down in Trie
      crawl = child.get(part);

      // If this is end of a word, then update prevMatch
      if (crawl.isEnd()) {
        prevMatch = level + 1;
      }
    }

    // If the last processed character did not match end of a word,
    // return the previously matching prefix
    if (!crawl.isEnd()) {
      return result.substring(0, prevMatch);
    } else {
      return result;
    }
  }

  class TrieNode {

    private String name;
    private Map<String, TrieNode> children;
    private boolean isEnd;

    public TrieNode(String name) {
      this();
      this.name = name;
    }

    public TrieNode() {
      this.name = "";
      this.children = new HashMap<>();
      this.isEnd = false;
    }

    public boolean isEnd() {
      return isEnd;
    }

    public void setEnd(boolean end) {
      isEnd = end;
    }

    public Map<String, TrieNode> getChildren() {
      return children;
    }
  }
}
