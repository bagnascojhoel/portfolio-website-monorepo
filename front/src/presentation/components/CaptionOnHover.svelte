<script>
    export let caption;
</script>

<div class="relative">
    <span class="show-clipboard-copy">
        <slot />
    </span>
    <span
        id="caption"
        class="border-solid border-primary border-2 rounded p-1 text-primary shadow-md"
        >{caption}</span
    >
</div>

<style>
    #caption {
        display: none;
        position: absolute;
        bottom: -110%;
        left: 0;
    }

    .show-clipboard-copy::after {
        content: '';
        display: box;
    }

    .show-clipboard-copy:hover ~ #caption {
        display: inline-block;
    }
</style>
