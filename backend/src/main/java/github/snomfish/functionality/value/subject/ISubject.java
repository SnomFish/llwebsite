package github.snomfish.functionality.value.subject;

public interface ISubject<T> {

    <A> A get(T context);
    <A> void set(T context, A a);

}
