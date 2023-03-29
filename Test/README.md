## 使用文档
使用方法参考Client.java  

### version 1.0  
完成基本功能，用反射来获取事件的泛型类型  
Bus.getinstance() 获取总线对象  
register(Observer<T>) 登记观察者， 通过反射获取泛型T类型和对应的Class对象，并放入Map  
unregister(Observer<T>) 注销观察者  
postEvent(Event<T>) event 的class 对象获取观察者列表，再将观察者放入event中，调用所有观察者的update方法   
### version 1.1 
使用Lambda的方式来注册观察者，会导致类型擦除，无法获取事件类型，现修改成用户自行传入事件的Class对象  
Bus.getinstance() 获取总线对象  
register(Observer<T>, t.getClass()) 登记观察者  
unregister(Observer<T>, t.getClass()) 注销观察者  
postEvent(Event<T>) event 的class 对象获取观察者列表，再将观察者放入event中，调用所有观察者的update方法 

### version 1.2
加入线程切换功能  
registerInUI(Observer<T>, t.getClass()) 登记观察者，其update()回调方法在主线程中执行  

