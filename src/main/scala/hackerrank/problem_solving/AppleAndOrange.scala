package hackerrank.problem_solving

import java.io._
import java.math._
import java.security._
import java.text._
import java.util._
import java.util.concurrent._
import java.util.function._
import java.util.regex._
import java.util.stream._

/*
    Source: https://www.hackerrank.com/challenges/apple-and-orange/problem

    Implement function:
        def countApplesAndOranges(s: Int, t: Int, a: Int, b: Int, apples: Array[Int], oranges: Array[Int])
    Where:
        s: integer, starting point of Sam's house location.
        t: integer, ending location of Sam's house location.
        a: integer, location of the Apple tree.
        b: integer, location of the Orange tree.
        apples: integer array, distances at which each apple falls from the tree.
        oranges: integer array, distances at which each orange falls from the tree.
 */

object AppleAndOrange {

    // Complete the countApplesAndOranges function below.
    def countApplesAndOranges(s: Int, t: Int, a: Int, b: Int, apples: Array[Int], oranges: Array[Int]) {
        def solve(items: Array[Int], d: Int): Int =
            items.map(_ + d).count(x => (s <= x && x <= t))

        println(solve(apples, a))
        println(solve(oranges, b))
    }

//  Input:
//  7 11
//  5 15
//  3 2
//  -2 2 1
//  5 -6
//
//  Output:
//  1
//  1

    def main(args: Array[String]) {
        val stdin = scala.io.StdIn

        val st = stdin.readLine.split(" ")

        val s = st(0).trim.toInt

        val t = st(1).trim.toInt

        val ab = stdin.readLine.split(" ")

        val a = ab(0).trim.toInt

        val b = ab(1).trim.toInt

        val mn = stdin.readLine.split(" ")

        val m = mn(0).trim.toInt

        val n = mn(1).trim.toInt

        val apples = stdin.readLine.split(" ").map(_.trim.toInt)

        val oranges = stdin.readLine.split(" ").map(_.trim.toInt)
        countApplesAndOranges(s, t, a, b, apples, oranges)
    }
}
