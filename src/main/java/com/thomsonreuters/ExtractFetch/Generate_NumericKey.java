package com.thomsonreuters.ExtractFetch;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;



public class Generate_NumericKey {

    public static void main(String a[]){
        Hasher hasher= Hashing.murmur3_128().newHasher();
        hasher.putString("67e8a3b4-f2f1-4286-90c4-611c5dbce973:ID-12", Charsets.UTF_8);
        HashCode hashCode=hasher.hash();

        System.out.println(hashCode.asLong());

    }
}
