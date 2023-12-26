import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

class Block {
    private String data;
    private String hash;
    private String prevHash;

    public Block(String data, String hash, String prevHash) {
        this.data = data;
        this.hash = hash;
        this.prevHash = prevHash;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "Block{" +
                "data='" + data + '\'' +
                ", hash='" + hash + '\'' +
                ", prevHash='" + prevHash + '\'' +
                '}';
    }
}

class MyBlockchain {
    private List<Block> chain;

    public MyBlockchain() {
        String hashFirst = hashGenerator("first_gen");
        Block genesis = new Block("gen_data", hashFirst, "0"); // Setting "0" as the prev_hash for the genesis block
        chain = new ArrayList<>();
        chain.add(genesis);
    }

    public void addBlock(String data) {
        Block prevBlock = chain.get(chain.size() - 1);
        String prevHash = prevBlock.getHash();
        String hashVal = hashGenerator(data + prevHash); // Concatenating data and prev_hash for hashing
        Block block = new Block(data, hashVal, prevHash);
        chain.add(block);
    }

    private String hashGenerator(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Block> getChain() {
        return chain;
    }
}

public class Main {
    public static void main(String[] args) {
        MyBlockchain blockchain = new MyBlockchain();
        blockchain.addBlock("A");
        blockchain.addBlock("B");
        blockchain.addBlock("C");

        for (Block block : blockchain.getChain()) {
            System.out.println(block.toString());
        }
    }
}
