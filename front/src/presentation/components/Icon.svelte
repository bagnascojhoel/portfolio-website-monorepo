<script lang="ts">
  import * as source from '@assets/icons/index';

  export let name: string;
  export let color: string;
  export let size: number = 36;
  export let label: string | undefined = undefined;

  $: fixedName = fixName(name);

  function fixName(name: string) {
    const sections = name.split('-');
    return sections
      .map((value, index) => {
        return index <= 0 ? value : value[0].toUpperCase() + value.substring(1);
      })
      .join('');
  }
</script>

<div class="inline-block">
  <svelte:component
    this={source[fixedName]}
    class={`inline-block ${$$props.class}`}
    fill={color}
    width={size}
    heigth={size}
  />
  {#if label}
    <span
      class="pl-2 font-mono font-base text-align-middle"
      style={`color: ${color}`}>{label}</span
    >
  {/if}
</div>
