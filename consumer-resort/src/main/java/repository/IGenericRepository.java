package repository;

public interface IGenericRepository<T> {

  void addDataForUniqueSkiers(T item);
  void addDataForLiftRidesOnDayAndLift(T item);
  void addDataForLiftRidesOnDayAndHour(T item);

}
