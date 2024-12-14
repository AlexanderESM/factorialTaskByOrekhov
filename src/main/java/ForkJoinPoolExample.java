import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

class FactorialTask extends RecursiveTask<Long> {
    private final int n; // Число, для которого нужно вычислить факториал

    // Конструктор принимает число, для которого нужно вычислить факториал
    public FactorialTask(int n) {
        this.n = n;
    }

    // Метод для рекурсивного вычисления факториала
    @Override
    protected Long compute() {
        if (n <= 1) {
            // Базовый случай: факториал 1 или 0 равен 1
            return 1L;
        } else {
            // Рекурсивный случай: делим задачу на две части
            FactorialTask subTask = new FactorialTask(n - 1);
            // Асинхронно выполняем подзадачу
            subTask.fork();
            // Выполняем текущую задачу (умножаем на n)
            long result = n * subTask.join();
            return result;
        }
    }
}

public class ForkJoinPoolExample {
    public static void main(String[] args) {
        int n = 10; // Вычисление факториала для числа 10

        // Создаем ForkJoinPool для параллельного выполнения задач
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // Создаем задачу для вычисления факториала
        FactorialTask factorialTask = new FactorialTask(n);

        // Запускаем задачу в ForkJoinPool и получаем результат
        long result = forkJoinPool.invoke(factorialTask);

        // Выводим результат
        System.out.println("Факториал " + n + "! = " + result);
    }
}

