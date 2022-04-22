package repository;

public interface IGenericRepository<T> {

  void addDataForDaysOnSkierAndSeason(T item);
  void addDataForLiftsOnSkierAndDay(T item);
  void addDataForTotalDayVerticals(T item);
  void addDataForVerticals(T item);

}
