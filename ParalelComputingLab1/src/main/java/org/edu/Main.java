package org.edu;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

public class Main {
    // Перевірка числа на простоту
    public static boolean isPrime(long n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    // Послідовний алгоритм
    public static int countTwinPrimesSequential(long n) {
        int count = 0;
        for (long i = 3; i <= n - 2; i += 2) {
            if (isPrime(i) && isPrime(i + 2)) {
                count++;
            }
        }
        return count;
    }

    // Паралельний алгоритм
    public static int countTwinPrimesParallel(long n, int numThreads) throws InterruptedException {
        AtomicInteger globalCount = new AtomicInteger(0);
        Thread[] threads = new Thread[numThreads];
        long range = (n - 2) / numThreads;

        for (int i = 0; i < numThreads; i++) {
            final long start = 3 + i * range;
            final long end;
            // Перевіряємо, чи є поточний потік останнім
            if (i != numThreads - 1) {
                // Якщо потік не останній, він обробляє стандартний відрізок (range)
                end = start + range;
            } else {
                // Якщо потік останній, він забирає весь залишок до n - 2
                // Це важливо, щоб не втратити числа через округлення при діленні
                end = n - 2;
            }
            threads[i] = new Thread(() -> {
                System.out.println("Потік "+ Thread.currentThread().getName() +" Почав роботу" );
                int localCount = 0;
                long current = start;

                // Переконуємось, що починаємо з непарного числа
                if (current % 2 == 0) {
                    current++;
                }

                for (long j = current; j <= end; j += 2) {
                    if (isPrime(j) && isPrime(j + 2)) {
                        localCount++;
                    }
                }
                globalCount.addAndGet(localCount);
                System.out.println("Потік "+ Thread.currentThread().getName() +" завершив роботу" );
            });
            threads[i].start();
        }

        for (Thread t : threads) t.join();
        return globalCount.get();
    }

    public static void main(String[] args) {
        String filename = "input_data.txt";
        try {
            /*System.out.print("Введіть n (або 0 для генерації випадкового): ");
            Scanner sc = new Scanner(System.in);
            long n = sc.nextLong();*/
            Scanner scanFile = new Scanner(new File(filename));

            long n = scanFile.nextLong();
            scanFile.close();
            if (n == 0) {
                n = 1_000_000 + new Random().nextInt(9_000_000);
                System.out.println("Згенеровано n = " + n);
            }

            // Збереження у файл
            PrintWriter writer = new PrintWriter(new File(filename));
            writer.println(n);
            writer.close();

            // Зчитування з файлу
            Scanner fileScanner = new Scanner(new File(filename));
            long inputN = fileScanner.nextLong();
            fileScanner.close();

            System.out.println("\nОбробка n = " + inputN);

            // Послідовний замір
            long startSeq = System.currentTimeMillis();
            int resultSeq = countTwinPrimesSequential(inputN);
            long endSeq = System.currentTimeMillis();

            // Паралельний замір
            int threads = Runtime.getRuntime().availableProcessors();
            long startPar = System.currentTimeMillis();
            int resultPar = countTwinPrimesParallel(inputN, threads);
            long endPar = System.currentTimeMillis();

            //Виведення результатів
            String output = String.format(
                    "Результат послідовно: %d, результат паралельно %d пар\n" +
                            "Час (послідовно): %.2f с\n" +
                            "Час (паралельно, %d потоків): %.2f с\n" +
                            "Прискорення: %.2fx",
                    resultSeq,resultPar, (double)(endSeq - startSeq)/1000, threads, (double)(endPar - startPar)/1000,
                    (double)(endSeq - startSeq) / (endPar - startPar)
            );

            System.out.println(output);

            // Збереження результату у файл
            PrintWriter resWriter = new PrintWriter(new File("result.txt"));
            resWriter.println(output);
            resWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
