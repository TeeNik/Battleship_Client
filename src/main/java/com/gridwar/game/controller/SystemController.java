package com.gridwar.game.controller;


public class SystemController implements IDisposable {

    public Map<Type, ISystem> systems;

    public SystemController() {
        systems = new Dictionary<Class, ISystem> {
            {
                ShootComponent.class, new ShootingSystem();
            },
            {
                MovementComponent.class, new MovementSystem();
            },
            {
                OperativeInfoCmponent.class, new OperativeInfoSystem();
            },
            {
                HealthComponent.class, new HealthSystem();
            },
            {
                CharacterActionComponent.class, new CharacterActionSystem();
            }
        } ;
    }

    public ISystem getSystem<T>(Class clazz) {
        return systems.get(clazz);
    }

    public void UpdateSystems() {
        foreach(var system in Systems.Values)
        {
            system.Update();
        }

        getSystem(ShootingSystem.class).OnUpdateEnd();
    }

    public void ProcessData(int entityId, ActionPhase phase) {
        var entity = Game.I.EntityManager.GetEntity(entityId);
        var comp = Utils.ActionTypeToComponent(phase.type);
        entity.GetEcsComponent(comp).Update(phase.component);
    }

    public bool IsProcessing() {
        return Systems.Values.Any(s = > s.IsProcessing());
    }


    public void Dispose() {
        foreach(var system in Systems.Values.Where(s = > s is IDisposable).Cast < IDisposable > ())
        {
            system.Dispose();
        }
    }
}